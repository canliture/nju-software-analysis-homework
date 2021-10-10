对应的作业要求文档在 `docs/ass4-PointerAnalysis.pdf`
看完前10节课的作业(Lecture 8/9/10)

### Pointer Analysis
指针分析；

本次作业将实现全程序的上下文不敏感的指针分析(whole-program context-insensitive);

如果你实现的指针分析是正确的，那么它会比CHA更加精确。(官方提供了一个常量传播的例子用于验证，这里我们暂时只考虑指针分析的实现即可)

同样，我们只需要考虑Java语言的一个子集特性即可。

#### 分析的语言特性
在Lecture 8里面有提到。

我们只处理两种类型的指针：
- Local Variable
- Instance fields

我们只处理5种跟指针相关的操作：
- new
- assign
- store
- load
- call

我们的指针分析并不仅仅处理virtual call，也处理special/static call

注：静态调用在ass5里面统一实现，ass4就不实现了


