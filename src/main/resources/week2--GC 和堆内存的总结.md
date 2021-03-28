GC 主要分7种：
1.serialGC
2.parallelGC
3.CMSGC
4.G1GC
5.ZGC
6.EpsilonGC
7.ShenandoahGC

1）
串行GC 即单线程GC，执行过程中都要STW，应用线程都要暂停。是最原始最简单的GC。YGC和FULLGC日志如下所示：
2021-03-28T13:47:36.433-0800: 0.172: [GC (Allocation Failure) 2021-03-28T13:47:36.433-0800: 0.172: 
[DefNew: 139776K->17472K(157248K), 0.0235575 secs] 139776K->40178K(506816K), 0.0236699 secs]
 [Times: user=0.02 sys=0.01, real=0.02 secs]
 
 
 2021-03-28T13:47:37.298-0800: 1.037: [Full GC (Allocation Failure) 2021-03-28T13:47:37.298-0800: 
 1.037: [Tenured: 349490K->348503K(349568K), 0.0473247 secs] 506274K->348503K(506816K), 
 [Metaspace: 2560K->2560K(1056768K)], 0.0474410 secs] 
 [Times: user=0.05 sys=0.00, real=0.05 secs]
 
 适用于单CPU环境下的client模式。
 
 2）
 并行GC，年轻代使用parallel scavenge ,年老代使用parallel old.日志如下。
 它适合于吞吐量优先地场景。是JAVA8默认的GC算法。
 这个算法关注的是最大程度提高吞吐量，因此年轻代和年老代回收都会STW
 
 2021-03-28T13:47:14.706-0800: 0.429: [GC (Allocation Failure) [PSYoungGen: 116216K->34722K(116736K)]
  372258K->326088K(466432K), 0.0135801 secs] 
 [Times: user=0.02 sys=0.02, real=0.02 secs]
 2021-03-28T13:47:14.720-0800: 0.443: [Full GC (Ergonomics) [PSYoungGen: 34722K->0K(116736K)] 
 [ParOldGen: 291366K->228678K(349696K)] 326088K->228678K(466432K), [Metaspace: 2560K->2560K(1056768K)], 0.0309791 secs] 
 [Times: user=0.09 sys=0.01, real=0.03 secs]
 
 
 3）
 CMS GC。年轻代使用parnew .年老代使用CMSGC。这个算法适合于关注低延迟的场景。
 这个算法是基于并行GC的改进。CMS GC分为6个阶段，其中只有2个阶段需要STW。其他4个阶段都是并发的
 且能和应用线程并行。
 需要注意的是，CMSGC在特殊情况下，会退化为串行GC。如concurrent mode failure 或promotion failed
 
产生这两种现象的两种原因：
A. Minor GC后， Survivor空间容纳不了剩余对象，将要放入老年代，老年代有碎片或者不能容纳这些对象，
就产生了concurrent mode failure, 然后进行stop-the-world的Serial Old收集器
解决办法：
-XX:UseCMSCompactAtFullCollection -XX:CMSFullGCBeforeCompaction=5 或者调大新生代或者Survivor空间

B. CMS是和业务线程并发运行的，在执行CMS的过程中有业务对象需要在老年代直接分配，例如大对象，
但是老年代没有足够的空间来分配，所以导致concurrent mode failure, 然后需要进行stop-the-world的Serial Old收集器。

解决办法：+XX:CMSInitiatingOccupancyFraction，调大老年带的空间，+XX:CMSMaxAbortablePrecleanTime

4)

G1GC 可以同时用于年轻代和老年代。他是CMSGC的进一步优化。他同样关注于低延迟场景。比较适用于大堆。G1把内存
划分为多个小块进行增量回收，每一块并不限定是年轻代还是年老代。
他是JAVA11的默认垃圾回收器。

5) ZGC是无停顿垃圾回收器。他的停顿时间进一步缩短。他的停顿可以控制在几毫秒

6）EpsilonGC是实验性的GC
7）ShenandoahGC类似于ZGC 
是redhat主推的GC



