对应的作业要求文档在 `docs/ass1-ConstantPropagationAnalysis.pdf`

实现常量传播，使用它提供的Bamboo框架即可，我们只需要提供meet/transfer函数；只考虑Java程序的一个子集；

- 只考虑int类型，4个算数运算 +,-,*,/
- 只需要关注等式的右侧是
  - Constant; 如: x = 1
  - Variable; 如: x = y
  - BinaryExpression; 如 x = a + b; x = 1 - c; x = 2 * 3

