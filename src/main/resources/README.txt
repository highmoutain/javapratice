题目：写代码实现Spring Bean的装配
有三种装配方式，
自动装配 代码是week5spring.AutoWireExample.java
java代码显式装配 代码是week5spring.JavaCodeExample.java
xml显式装配  代码是week5spring.XmlLoad.java

题目：给前面课程提供的 Student/Klass/School 实现自动配置和 Starter
工程为：
mySchoolStarter

题目：
1)使用 JDBC 原生接口，实现数据库的增删改查操作。
2)使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
3)配置 Hikari 连接池，改进上述操作

代码为：
database.jdbc.MysqlJdbc为题目的1和2部分
database.hikari为hikari实现，运行HikariApplication即可