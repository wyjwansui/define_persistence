# mybatis

## **一、简单题**

### 1、Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理？

答：动态sql根据参数的判断是否拼接动态的sql片段，减少重复sql编写

动态sql有：① if 和choose ② foreach ③ where、set和trim ④ include

执行原理：

XMLStatementBuilder：解析Mapper.xml文件中各个节点，比如select,insert,update,delete节点，内部会使用 XMLScriptBuilder 处理节点的sql部分，遍历产生的数据会丢到Configuration的mappedStatements中。当我们需要某个动态sql时候，根据动态sql的Id到获取到MappedStatement拼接到具体的sql中

### 2、Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？

答：支持

​	延迟加载主要是在关联查询中，需要在sqlManConfig中开启延迟加载，在封装ResultMap时候可以指定具体的关联的对象进项延迟加载。通过动态代理的方式实现，拦截 我们指定关联对象的方法，当调用该方法时，才去加载数据。 在出来响应的数据ResultSet时候，判断是否开启的延迟加载，如果开启延迟加载，则创建代理对象并保存。当我们使用对象的指定方法时候，则执行延迟加载

### 3、Mybatis都有哪些Executor执行器？它们之间的区别是什么？

答：BatchExecutor、CachingExecutor、ClosedExecutor、ReuseExecutor和SimpleExecutor

BatchExecutor：主要是处理批量执行

CachingExecutor：处理二级缓存

ClosedExecutor：已经被关闭的执行器

ReuseExecutor：可以重复使用的执行器，该执行器会缓存同一个sql的Statement，省去重建Statement

SimpleExecutor：每次操作都创建statement对象

### 4、简述下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景。三个方面来作答）？

答：一级缓存：默认开启，存储结构是Map集合，Map的key是由statementId + param + boundeSql + rowBounds 组成；

作用范围是在sqlSession级别，也就是在同一个sqlSession中查询执行同样的sql时，才会去查询缓存；当我们更新数据（增加，修改和删除操作），也就是需要提交事务的操作时，就会清空缓存。

二级缓存：默认不开启，需要手动开启，存储结构也是Map集合；作用范围：是Mapper级别的缓存，也就多个SqlSession在操作同一个Mapper下的sql时，这些sqlSession共用二级缓存；失效范围：也是更新操作时，会刷新二级缓存。

### 5、简述Mybatis的插件运行原理，以及如何编写一个插件？

答：原理：在创建Executor、StatementHandler、parameterHandler和ResultHandler这个四个对象的时候，不是直接返回该对象，而是 通过拦截器链 interceptorChain.pluginAll(xxxHandler)，获取到所有的拦截器，每个拦截器将已创建的对象进行封装，使用插件为每一个对象创建代理对象，这个代理对象可以拦截各自的所有执行。

自定义插件：需要实现一个interceptor接口，重写接口中的三个方法

```java
	/**
	 * 只要被拦截的目标对象的目标方法被执行时，就会还行该方法
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		return invocation.proceed();
	}
	/**
	 * 主要是把当前的拦截器生成代理存到拦截器链中，生成目标对象的代理对象
	 */
	@Override
	public Object plugin(Object target) {
		Object wrap = Plugin.wrap(target, this);
		return wrap;
	}
	/**
	 * 读取配置,当前插件所需的参数
	 */
	@Override
	public void setProperties(Properties properties) {
		System.out.println("获取到配置文件的参数：" + properties);
	}

```

然后在我们自定义插件类上添加@Intercepts注解，定义我们自定义插件，对哪个对象（Executor、StatementHandler、parameterHandler和ResultHandler）的哪一个方法进项拦截，指定该方法的参数的类型

最后在sqlMapConfig.xml注册插件，设置插件所需要的参数

```xml
   <plugins>
        <plugin interceptor="com.lagou.multiple.plugin.MyPlugin">
             <property name="name" value="程林"/>
         </plugin>
    </plugins>
```

