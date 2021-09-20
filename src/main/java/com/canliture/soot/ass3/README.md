对应的作业要求文档在 `docs/ass3-ClassHierarchyAnalysis.pdf`
看完前6节课的作业(Lecture 7)

### Class Hierarchy Analysis

这个作业的目的就是用CHA来实现调用图的构造;

如果你的实现是正确的话，那么你得到的流程间的常量传播会比流程内的常量传播又更高的精度;
(官方作业应该已经实现了流程间常量传播，你的目的就是提供给它调用图即可)

正如第1个作业一样，你只需实现Java语言的一部分特性

### 分析的语言特性范围
你需要处理Java语言中的4中调用
- invokestatic
- invokespecial
- invokeinterface
- invokevirtual

注意 soot.FastHierarchy类提供了类的继承信息





