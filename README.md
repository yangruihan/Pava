# 《两周自制脚本语言》总结

## 一、词法分析（Lexical Analysis）

词法分析的目的在于将输入的源代码（一大串字符文本）拆解成一个一个基本组成单元（Token），并且在拆解的过程中，提炼出 Token 的类型（比如：字符串，标识符，数值等）等信息，为后面语法分析打下基础。

在该书中， 通过正则表达式的方式来完成此任务。

为了简单起见，这里我们设定语法规则如下：

| 类型   | 具体语法规定                             |
| ---- | ---------------------------------- |
| 注释   | 以`//`开头的内容                         |
| 数值   | 暂定只支持整数，为了简单起见09这样的整数也认为是合法的       |
| 字符串  | `""`内的内容，支持`\\`、`\"`、`\n`转义字符      |
| 标识符  | 以字母或下划线开头，后接字母数字下划线的变量名；各种操作符、标点符号 |

具体正则表达式为：`"\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";`

上述正则表达式与上表中的内容是一一对应的，匹配时首先跳过这一行的空格（`"\\s*"`），后面的内容都为有效的内容，因此用一个大的括号括起来，便于得到其内容。

在这个大的括号之中：

第一对括号中的内容`//.*`用于匹配注释

第二对括号中的内容`"[0-9]+"`用来匹配数值类型的 Token；

第三对括号内的内容`\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\"`用来对匹配字符串类型的 Token，由于字符串本身`\`需要转义，正则表达式中的`\`同样需要转义，因此这里的`\`非常的多，不过还是很容易分析的：`\\\\\"`这一部分用来匹配字符串中的`\"`，`\\\\\\\\`用来匹配字符串中的`\\`，`\\\\n`用来匹配字符串中的`\n`，剩下`[^\"]`用来匹配一切非`"`字符，至此整个字符串的匹配模式就完成了；

剩下的内容`[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?`用于匹配标识符，为了简单起见，我们对标识符的定义非常广泛，日后可以更加细化。

整个正则表达式分析完后，对源代码进行词法分析就是对源代码进行正则表达式的模式匹配，通过一行一行地遍历整个源代码，对其中每一行进行单独解析，设置正则式的范围，将该行中所有满足要求的字符串构建成不同类型的 Token，并保存到一个序列中，以便后续语法分析。

## 二、语法分析（Grammatical Analysis）

词法分析后，我们得到了一系列 Token，但是如何知道哪些 Token 是一句完整的语句呢，他们组成的语句的作用是什么呢？这就需要用到语法分析。语法分析中有一个非常重要的概念：抽象语法树（abstract syntax tree，AST）。将一系列 Token 构建成为一颗抽象语法树就是语法分析的具体作用。

表达式`s = 1 + 2`经过上面的词法分析，会得到`['s', '=', '1', '+', '2']`这样的一个 Token 序列，其中`s`、`=`、`+`为标识符类型的 Token，`1`、`2`为数值类型的 Token。

为了表达该表达式的含义，我们将其转换成形如下面展示的一颗抽象语法树：

```
	=
   / \
  s   +
     / \
    1   2
```

此时我们便可以很清晰的看到：`1`和`2`是要进行`+`运算的，`s`和`1+2`的结果要进行`=`运算。

同样的，只要我们将源代码中的 Token 转换成一颗抽象语法树，那么我们便能得到具体的语句及其语义，因此这一阶段被称为**语法分析**。

但是，要想将 Token 序列转换成一颗抽象语法树，首先我们得定义我们自己的文法，比如`Java`中的`if`语句、`while`语句等。如何表示定义好的文法呢，这里要用到另一个概念 BNF（**巴科斯范式**，Backus Normal Form），这是一种用于表示上下文无关文法的语言。

下面是用 BNF 来表示我们的设计好的文法：

```
primary			:	"(" expr ")" | NUMBER | IDENTIFIER | STRING
factor			:	"-" primary | primary
expr			:	factor {OP factor}
block			:	"{" [ statement ] { (";" | EOL) [ statement ]}
simple			:	expr
statement		:	"if" expr block { "else" block }
				|	"while" expr block
				|	simple
program			:	[ statement ] (";" | EOL)
```

文法设计好后，只剩下最后一步，但也是最困难的一步，用代码去实现。这里用到了原书作者编写的一个 Parser 类，通过该类可以非常直观的将 BNF 所设计的内容表示出来：

```java
Parser expr0 = rule();

Parser primary = rule(PrimaryExpr.class).or(
  				 rule().sep("(").ast(expr0).sep(")"),
				 rule().number(NumberLiteral.class),
  				 rule().identifier(Name.class, reserved),
			     rule().string(StringLiteral.class));

Parser factor = rule().or(rule(NegativeExpr.class).sep("-").ast(primary), primary);

Parser expr = expr0.expression(BinaryExpr.class, factor, operators);

Parser statement0 = rule();

Parser block = rule(BlockStmnt.class)
    		   .sep("{").option(statement0)
  			   .repeat(rule().sep(";", Token.EOL).option(statement0))
			   .sep("}");

Parser simple = rule(PrimaryExpr.class).ast(expr);

Parser statement = statement0.or(
 rule(IfStmnt.class).sep("if").ast(expr).ast(block).option(rule().sep("else").ast(block)),
 rule(WhileStmnt.class).sep("while").ast(expr).ast(block),
 simple);

Parser program = rule().or(statement, rule(NullStmnt.class)).sep(";", Token.EOL);
```

到此，语法分析也完成了。