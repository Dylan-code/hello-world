### 假设由整数构成的文件里的整数为 2、3、4、4、5、6、3、2、7、8、9、5、5、5
#### 一、Map 获得如下k-v
- <2,1>、<3,1>、<4,1>、<4,1>、<5,1>、<6,1>、<3,1>、<2,1>、<7,1>、<8,1>、<9,1>、<5,1>、<5,1>、<5,1>
#### 二、通过k进行分组
#### 三、Reduce 获得如下k-v, k为出现过的整数，v为该整数出现过的次数
- <2,1>、<3,2>、<4,2>、<5,4>、<6,1>、<7,1>、<8,1>、<9,1>
#### 四、比较k-v中的k即可求出最大值
#### 五、所有k的数据相加然后除以k的数量即可求出所有整数的平均值
#### 六、求出k的数量就是所有不同整数出现的次数