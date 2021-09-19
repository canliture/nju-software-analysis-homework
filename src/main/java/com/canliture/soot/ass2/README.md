对应的作业要求文档在 `docs/ass2-DeadCodeDetectionAnalysis.pdf`

为了实现死代码消除, 你需要完成constant propagation analysis 和 liveness analysis

所以这个作业的前提是要完成第一个作业; 同样，我们只考虑Java语言的子集（小部分特性）


什么是死代码：
- 不可达的语句(从不会被执行)；或者被执行，但是它的结果从不被用做任何其它的计算
- 消除死代码并不会影响程序的最终执行结果；相反它能够精简程序的代码，提升性能

困难就在于怎么找到死代码；在这个作业中你将实现一个分析，用于检测死代码，它会通过两个数据流分析来实现；
- constant propagation
- live variable analysis

在这个作业中我们关注于两种死代码
- unreachable code 不可达代码
- dead assignment 死赋值(赋值之后再也没被使用)


### Unreachable Code
我们考虑两种不可达代码
- control-flow unreachable code: 控制流不可达
- unreachable branch: 不可达分支

#### control-flow unreachable code
不存在从entry到某个代码的执行路径，那么这个代码不可达

```java
int controlFlowUnreachable() {
  int x = 1;
  return x;
  int z = 42; // control-flow unreachable code
  foo(z); // control-flow unreachable code
}
```
这种很容易检测, 从entry遍历cfg, 然后标记可达节点；遍历完之后，没被标记的就是不可达代码

#### unreachable branch
如果分支的条件判断变量是个常量, 那么它的true/false分支中一定有一个是不可达的；我们称这个不可达的分支为unreachable branch；

在这个分支内的代码是不可达的；

```java
int unreachableBranch() {
  int a = 1, b = 0, c;
  if (a > b) 
      c = 2333; 
  else 
      c = 6666; // unreachable branch
  return c;
}
```
为了检测这样的问题，我们需要首先执行常量传播，它会告诉我们是否条件变量为常量；
那么我们在遍历cfg的过程中可以跳过相关的不可达分支


### Dead Assignment
一个局部变量被赋值，但是后续的指令从没读取过这个变量，我们我们认为这个赋值为dead assignment

```java
int deadAssign() {
  int a, b, c;
  a = 0; // dead assignment
  a = 1;
  b = a * 2; // dead assignment
  c = 3;
  return c;
}
```
为了检测dead assignment，我们需要首先执行live analysis。对于一个赋值语句，如果它的左侧变量为死变量(not live)，那么我们能够标记它为死赋值；

当然，要注意以下特殊情况：

很多时候`x = expr`在x not live时并不能将此赋值移除。因为赋值右侧的expr可能会有某些副作用。

比如`x = foo()`, 有时候foo会修改全局变量(全局静态字段, this字段等)。

出于保守策略，对于这种情况，即时当x为not live，我们也不认为此赋值语句是dead code；所以我们并不会将此语句给移除，优化掉。

### 处理的语言特性(Scope)
在这次作业中，只处理Java语言的子集特性。常量传播处理和作业1要求的一样。

在本次作业中，
- 对于`条件判断语句`，我们需要处理6个额外的`整数类型的比较`: `==, !=, >=, >, <=, <`
- 为了保持CFG足够简单，我们并不考虑异常




