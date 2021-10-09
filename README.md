这里是南京大学软件分析课程实验作业的非官方实现

### 文档
- docs/org为南大课程的作业原题pdf
- docs/soot为soot参考资料
- src/main/java/com/canliture/soot/每个子目录下的作业都有一个README，作为作业的简单说明

### 代码
- com.canliture.soot.ass1
  - 第一次作业的代码实现
  - 常量传播
- com.canliture.soot.ass2
  - 第二次作业的代码实现
  - 常量传播 + 活性检测 应用于死代码消除
- com.canliture.soot.ass3
  - 第三次作业的代码实现
  - CHA用于构造调用图
- com.canliture.soot.ass4
  - 第四次作业的代码实现
  - 流不敏感指针分析
- com.canliture.soot.ass5
  - 第五次作业的代码实现
  - 流敏感指针分析
  
### 测试
src/test/java/目录下
com.canliture.soot.ass{n}