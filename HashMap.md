# 常用方法
## `put`方法
### 描述: 
最常用的方法之一,用来向hash桶中**添加** *键值对*.但是这个方法并不会去执行实际操作.而是委托`putVal`方法进行处理
### 代码:
```java
public V put(K key, V value) {
	// 这次个调用分别指定了hash,key,value,替换现有值,非创建模式
    return putVal(hash(key), key, value, false, true);
}
```
> 这里调用了`hash`方法获取了*key*的**hash**,后面单独说这个**hash**的意义
## `putVal`方法
### 描述:
实际执行`put`操作的方法.
### 代码:
```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
	// tab - 当前hash桶的引用
	// p - key所代表的节点(此节点不一定是目标节点,而仅仅是hash与桶长度的计算值相同而已)(它不为空时可能是链表或红黑树)
	// n - 当前桶的容量
	// i - key在桶中的下标(同p,不代表目标节点)
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    // 初始化局部变量tab并判断是否为空,初始化局部变量n并判断是否为0
    // PS: 源码中大量的使用了这种书写方法,不知道放在某写大厂里会怎么样(斜眼笑)
    if ((tab = table) == null || (n = tab.length) == 0)
        // 当tab为空或n为0时,表明hash桶尚未初始化,调用resize()方法,进行初始化并再次初始化局部变量tab与n
        n = (tab = resize()).length;
    
    // 初始化p与i
    // 这里使用了(n - 1) & hash的方式计算key在桶中的下标.这个在后面单独说明
    // 当p是否为空
    if ((p = tab[i = (n - 1) & hash]) == null)
    	// p为空,调用newNode方法初始化节点并赋值到tab对应下标
        tab[i] = newNode(hash, key, value, null);
    else {
    	// p不为空,发生碰撞.进行后续处理
    	
    	// e - 目标节点
    	// k - 目标节点的key
        Node<K,V> e; K k;
        
        // 判断key是否相同.(这里除了比较key以外,还比较了hash)
        // 注意,这里同时初始化了局部变量k,但是在第二组条件不满足的情况下,没有使用价值,可以被忽略
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
        	// key相同,将e(目标节点)设置为p
            e = p;
        // 判断节点是否是红黑树
        else if (p instanceof TreeNode)
        	// 确定时,直接委派处理
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
        	// 走到这里,代表当前节点为普通链表,进行遍历查找
        	// 变量binCount只作为是否达到tree化的阈值判断条件.
            for (int binCount = 0; ; ++binCount) {
            	
            	// 获取链表的下一个元素,并赋值到e(此时e是一个中间变量,不确定是否是目标节点)
            	// 第一次for循环时,p代表hash桶中的节点(同时也是链表的头部节点),之后一直等于p.next
                if ((e = p.next) == null) {
                	// 链表遍历到末尾
                	
                	// 向链表中追加新的节点
                    p.next = newNode(hash, key, value, null);
                    
                    // 判断当前链表长度是否达到tree阈值
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                    	// 调用treeifyBin方法直接处理
                        treeifyBin(tab, hash);
                    // 中断循环
                    // 注意,此时局部变量e=null
                    break;
                }
                
                // 能走到此处,说明链表未结束,比较e的k是否相同(hash与==)
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                	// key相同
                    break;
                
                // e既不为null也不是目标节点,赋值到p,准备进行下次循环
                p = e;
            }
        }
        
        // 判断e是否存在
        if (e != null) { // existing mapping for key
        	// e不等于null说明操作为"替换"
        	
        	// 缓存老值
            V oldValue = e.value;
            // 判断是否必须替换或老值为null
            if (!onlyIfAbsent || oldValue == null)
            	// 必须替换或老值为空,更新节点e的value
                e.value = value;
            // 调用回调
            afterNodeAccess(e);
            // 返回老值
            // 注意,这里直接返回了,而没有进行modCount更新与下面的后续操作
            return oldValue;
        }
    }
    
    // 除了更新链表节点以外,都会走到这里(putTreeVal的返回值是什么有待确认)
    // modCount+1
    ++modCount;
    // size+1(元素数量+1)
    // 判断是否超过阈值
    if (++size > threshold)
    	// 重置大小
        resize();
    // 调用后置节点插入回调
    afterNodeInsertion(evict);
    return null;
}
```
## `resize`方法
### 描述: 
用于添加键值对后的扩容与对槽重新分布的操作
### 代码:
```java
final Node<K,V>[] resize() {
    //----------------------------------- 新容量与阈值计算 -----------------------------------
	
	// 缓存桶引用
    Node<K,V>[] oldTab = table;
    // 缓存老的桶的长度,桶为null时,使用0
    // 注意,这里用的是oldTab.length,而不是size
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    // 缓存阈值
    int oldThr = threshold;
    // 新桶容量与阈值
    int newCap, newThr = 0;
    
    // 老容量大于.这一般代表这个桶已经经过了resize的数次处理
    if (oldCap > 0) {
    	
    	// 老容量大于MAXIMUM_CAPACITY = 1 << 30 = 1073741824
    	// 容量计算方式为n<<1,当oldCap >= MAXIMUM_CAPACITY时,再次执行位移.其可能的最大值就是Integer.MAX_VALUE
        if (oldCap >= MAXIMUM_CAPACITY) {
        	// 设置阈值为Integer.MAX_VALUE
            threshold = Integer.MAX_VALUE;
            // 直接return.放弃全部后续处理
            return oldTab;
        }
        // 使用oldCap << 1初始化newCap
        // 当oldCap小于MAXIMUM_CAPACITY并且oldCap大于DEFAULT_INITIAL_CAPACITY(16)时
        // 此时newCap可能已经大于MAXIMUM_CAPACITY并且newThr=0或者newCap很小(小于16>>2)并且newThr=0
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
        	//设置newThr为oldThr << 1(这里没有做正确性校验,待查)
            newThr = oldThr << 1; // double threshold
    }
    // 判断老阈值是否大于0
    // 走到这说明oldCap==0,并且使用了包含initialCapacity参数的构造器构造了这个map,且没有被添加过元素
    else if (oldThr > 0) // initial capacity was placed in threshold
    	// 使用将新容量复制为老阈值(newCap此时为0)
    	// 注意: 在使用了包含initialCapacity参数的构造方法时,其threshold已经被计算为2的n次幂
        newCap = oldThr;
    else {               // zero initial threshold signifies using defaults
    	// 默认方法,当使用无参构造方法时,会出现oldThr与oldCap都等于0的情况
    	// 使用默认初始化容量赋值到newCap
        newCap = DEFAULT_INITIAL_CAPACITY;
    	// 使用默认初始化容量与加载因子相乘赋值到newThr
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
    
    // 统一处理newThr
    if (newThr == 0) {
    	// 新容量与加载因子相乘
        float ft = (float)newCap * loadFactor;
        // 当newCap与ft均小于MAXIMUM_CAPACITY时,newThr=ft.否则newThr=Integer.MAX_VALUE
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    
    //----------------------------------- 元素重排 -----------------------------------
    // 更新threshold
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
    // 更新桶对象(此时是空的)
    Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    // 判断老桶是否为空
    if (oldTab != null) {
    	// 老桶不为空.进行遍历
        for (int j = 0; j < oldCap; ++j) {
        	// 桶元素
            Node<K,V> e;
            // 进行桶元素获取
            // 判断桶元素是否存在(因为使用(n-1)&hash的方式进行计算,所以经常会出现这种情况)
            if ((e = oldTab[j]) != null) {
            	// 删除引用
                oldTab[j] = null;
                // 判断桶元素是否有下一个元素
                if (e.next == null)
                	// 没有下一个元需.使用相同的算法计算在新桶中的下标并赋值
                    newTab[e.hash & (newCap - 1)] = e;
                // 桶元素存在next,判断是否为TreeNode
                else if (e instanceof TreeNode)
                	// 进行委派执行
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else { // preserve order
                	// 对于链表结构,拆分到高位与低位两组
                	
                	// loHead与loTail非别代表低位头与低位尾
                    Node<K,V> loHead = null, loTail = null;
                	// hiHead与hiTail非别代表高位头与高位尾
                    Node<K,V> hiHead = null, hiTail = null;
                    // next
                    Node<K,V> next;
                    // 已经存在遍历目标,直接使用do while
                    do {
                    	// 拿到e的next.
                        next = e.next;
                        // 判断e的hash是否是高位
                        // 判断原理如下.
                        // 首先oldCap恒定为2的n次幂,二进制表达为1000...
                        // 下标计算方程为(n-1)&hash
                        // 带入n后,为...111&hash
                        // 当n=111时,hash为1101,结果为101
                        // 当n=1111时,hash为1101,结果为1101.表示为高位(注意hash的高位)
                        // 当n=1111时,hash为0101,结果为101.表示为低位(注意hash的高位)
                        // 这样就,可以直接求出新的下标.但是,这种方式需要对所有的元素进行重新计算,非常低效
                        // 所以jdk使用了一个特别的方法.就是直接比较最高位,当一个hash与数组长度(也就是n的n次幂)时,如1101&1000
                        // 当结果等于0时,代表这个hash是低位hash,其他就是高位hash
                        if ((e.hash & oldCap) == 0) {
                        	// 低位
                        	// 判断低位尾部是否存在
                            if (loTail == null)
                            	// 不存在,代表头部也没有,进行初始化
                                loHead = e;
                            else
                            	// 存在,追加到尾部的next
                                loTail.next = e;
                            // 更新尾部
                            loTail = e;
                        }
                        else {
                        	// 高位
                            if (hiTail == null)
                            	// 不存在,代表头部也没有,进行初始化
                                hiHead = e;
                            else
                            	// 存在,追加到尾部的next
                                hiTail.next = e;
                            // 更新尾部
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    
                    // 进行收尾处理
                    // 判断低位是否为空
                    if (loTail != null) {
                    	// 不为空
                    	// 清除末尾元素的next.当loTail是链表倒数第二个元素且倒数第一个元素是高位元素时,需要清空loTail的next对高位元素的引用
                        loTail.next = null;
                        // 低位使用原下标进行保存
                        newTab[j] = loHead;
                    }
                    if (hiTail != null) {
                    	// 不为空
                    	// 清除末尾元素的next.理由同上但判断方式相反
                        hiTail.next = null;
                        // 低位使用原下标+原容量进行保存
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    // 返回newTab
    return newTab;
}
```
## `treeifyBin`方法
### 描述: 
当某槽内的链表长度大于阈值后,此方法会被调用.将槽内对应位置的链表替换为红黑树.
注意:这个方法内只是将链表替换成了红黑树对象`TreeNode`.此时还是链状结构,没有组装成红黑树的结构.需要在最后带用链表头部对象的`TreeNode.treeify`方法完成树化
### 代码:
```java
final void treeifyBin(Node<K,V>[] tab, int hash) {
	// n - 代表参数tab长度
	// index - tab中表示hash的下标
	// hash - 待处理的链表节点hash
	// e - 目标节点
    int n, index; Node<K,V> e;
    // 判断tab是否为空或tab长度MIN_TREEIFY_CAPACITY=64
    // 也就是说,在桶中单个链表长度可能已经达到要求(如putVal中的binCount >= TREEIFY_THRESHOLD - 1),但是桶容量未达标时,也不会进行tree化
    if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
    	// 表是空的或表容量小于MIN_TREEIFY_CAPACITY
    	// 重置大小
        resize();
    // 可以tree化,检查链表节点是否存在
    else if ((e = tab[index = (n - 1) & hash]) != null) {
    	// 链表节点存在
    	
    	// 树节点头与尾
        TreeNode<K,V> hd = null, tl = null;
        // 已经有第一个目标,直接do while
        do {
        	// 构造一个TreeNode.(这里没有额外逻辑,仅仅是使用当前的e创建了TreeNode)
        	// 注意,这里的Tree继承自LinkedHashMap.Entry,内部包含了before与after的双向链表.但是TreeNode又自行实现了双向链表prev与next,并没有使用前者的数据结构
            TreeNode<K,V> p = replacementTreeNode(e, null);
            // 判断尾部是否为空
            if (tl == null)
            	// 初始化头部
                hd = p;
            else {
            	// 尾部不为空
            	// 设置上一个节点
            	// 设置尾部下一个节点
                p.prev = tl;
                tl.next = p;
            }
            // 交换尾部
            tl = p;
        } while ((e = e.next) != null);
        
        // 赋值并判断头部节点是否为空
        if ((tab[index] = hd) != null)
        	// 调用TreeNode的treeify组装红黑树
            hd.treeify(tab);
    }
}
```
> 即使被调用,这个方法也不保证替换对应槽内的链表到红黑树.这还需要检查当前桶的容量是否达到阈值`MIN_TREEIFY_CAPACITY`
## `TreeNode.treeify`方法
### 描述:
将链表结构的数据转换成红黑树结构的数据的实际执行者(此时链表中的所有对象已经是`TreeNode`类型的了) 
### 代码:
```java
final void treeify(Node<K,V>[] tab) {
	// 根节点(黑色节点)
    TreeNode<K,V> root = null;
    // 进行迭代.(当前this作用域位于TreeNode实例)
    // x表示当前遍历中的节点
    for (TreeNode<K,V> x = this, next; x != null; x = next) {
        // 缓存next
    	next = (TreeNode<K,V>)x.next;
    	// 保证当前节点左右节点为null
        x.left = x.right = null;
        // 判断是否存在根节点
        if (root == null) {
        	// 不存在
        	// 跟节点没有父级.所以设置为null
            x.parent = null;
            // 红黑树中,根节点是黑色的
            x.red = false;
            // 保存到局部变量
            root = x;
        }
        else {
        	// 跟节点已确认
        	
        	// 缓存key
            K k = x.key;
            // 缓存hash
            int h = x.hash;
            // key类型
            Class<?> kc = null;
            // -------------------- 对跟节点进行遍历,查找插入位置 --------------------
            // p是插入节点的父节点
            for (TreeNode<K,V> p = root;;) {
            	// dir - 用来表达左右.
            	// ph - 父节点hash
                int dir, ph;
                // 父节点key
                K pk = p.key;
                
                // -------------------- 判断插入到左还是右节点 --------------------
                // 初始化父节点hash
                // 判断父节点hash是否大于当前节点hash
                if ((ph = p.hash) > h)
                	// dir = -1 插入节点在父节点左侧
                    dir = -1;
                // 判断父节点hash是否小于当前节点hash
                else if (ph < h)
                	// dir = 1 插入节点在父节点右侧
                    dir = 1;
                // 父节点hash等于当前节点hash,进行额外的处理
                // 这里使用了基于Class的一些处理办法,最终保证了dir的正确值(不为0) TODO 待补充 
                else if ((kc == null &&
                          (kc = comparableClassFor(k)) == null) ||
                         (dir = compareComparables(kc, k, pk)) == 0)
                    dir = tieBreakOrder(k, pk);


                // -------------------- 获取左或右节点并进行操作 --------------------
                // 缓存插入节点的父节点
                TreeNode<K,V> xp = p;
                // 使用dir获取父节点对应的左或右节点,并且检查这个节点是否为null.不为null时,进入下一次循环
                if ((p = (dir <= 0) ? p.left : p.right) == null) {
                	// 父节点左或右节点为null
                	
                	// 设置父级节点
                    x.parent = xp;
                    // 再次判断左右
                    if (dir <= 0)
                    	// 将父节点的左子节点复制为当前节点
                        xp.left = x;
                    else
                    	// 将父节点的右子节点复制为当前节点
                        xp.right = x;
                    // 进行平衡
                    root = balanceInsertion(root, x);
                    // 退出查找插入位置的循环,进行下一个元素的插入
                    break;
                }
            }
        }
    }
    
    // 因为在进行旋转操作时,可能会修改根节点到其他节点.导致桶中的直接节点为分支节点,所以需要进行修正
    moveRootToFront(tab, root);
}
```
## `hash`方法
### 描述:
`HashMap`自己使用的**hash**的计算方式.作为*key*比较,*index*的计算依据.它通过对象原**hash**与其**高16位**进行`^`运算,而得出一个新值做回hash.
### 代码:
```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```
> 这里之所以没有直接使用key的hash.是为了应对当key的hash分布非常差的时候,会间接导致  
> hash桶的分布非常差,从而影响性能.所以使用原hash异或(XOR)原hash的高16位,作为实际使用的hash  
> 这里之所以使用16:16,而不是8:8:8:8或其他值,是因为jdk开发者充分考虑了时间,效率,性能等各方面  
> 的情况后的折中选择.  
> 同时也是因为当前jdk大多数的hash已经有了较好的分布,所以也不需要进行过多的处理  
> 计算过程如下  
> 10000000000000000000000000000000  
> 00000000000000001000000000000000  
> 10000000000000001000000000000000  
## `tableSizeFor`方法
### 描述:
一般用作`threshold`的初始化工作.他会返回一个大于输入值的最小的2的幂.已经是2的幂时会再次返回它.
### 代码:
```java
static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```
> 直接上流程(暂时忽略cap - 1部分,最后说)  
> 10000    16  n初始状态  
> 11000    24  n|=n>>>1 等价于 n = 10000|01000 = 11000  
> 11110    30  n|=n>>>2 等价于 n = 11000|00110 = 11110  
> 11111    31  n|=n>>>4 等价于 n = 11110|00001 = 11111  
> 11111    31  略  
> 11111    31  略  
> 100000   32  n+1  
> 最后,通过+1,将...111变为100...,即2的n次幂  
>   
> 这里使用了一个很有意思的方式完成了工作,就是输入值的最高有效位.  
> 通过不断的向低位复制最高有效位(1),将所有低位换为1,最终这个值等于(2^n)-1.同时  
> 也是当前数字的最高位能表达的最大值  
> 那么,再对这个值+1就可以使这个值变成2^n.也就是大于输入值的最小的2的幂  
>   
> cap - 1的作用:  
> 如果输入值已经是2的幂,那么这个方法应该直接返回他.直接进行-1,使用原逻辑即可  

# 内部类
## `HashIterator`
### 描述:
HashMap自己实现的迭代器.主要用来约束对父类成员的引用.同时实现了remove,nextNode,hasNext等必须方法.为了方便子类实现,在nextNode方法中直接返回了Node类型对象.用来直接获取key与value
### 代码:
```java
abstract class HashIterator {
    /**
    * 下一个节点 
    */
    Node<K,V> next;        // next entry to return
    /**
    * 当前节点 
    */
    Node<K,V> current;     // current entry
    /**
    * 修改计数
    */
    int expectedModCount;  // for fast-fail
    /**
    * 当前下标(对于父级成员变量table来说,它指向桶中的一个槽(slot))
    */
    int index;             // current slot

    /**
    * 构造方法 
    */
    HashIterator() {
    	// 缓存修改计数
        expectedModCount = modCount;
        // 缓存桶 
        Node<K,V>[] t = table;
        // 进行置空(这一步是必须的吗????) 
        current = next = null;
        // 索引置0(为啥?????) 
        index = 0;
        // 检查桶是否已经初始化 
        if (t != null && size > 0) { // advance to first entry
        	// 提前获取并保存next
            do {} while (index < t.length && (next = t[index++]) == null);
        }
    }

    public final boolean hasNext() {
        return next != null;
    }

    final Node<K,V> nextNode() {
    	// 指向当前桶
        Node<K,V>[] t;
        // 缓存next.准备替换next.同时e作为结果返回
        Node<K,V> e = next;
        // fast-fail
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
        // next不应为空
        if (e == null)
            throw new NoSuchElementException();
        // -------------------- 寻找next --------------------
        // 设置current为e,next为e.next
        // 判断next是否为null
        // 如果为空,获取当前桶
        // 判断桶是否为空(能走到这里,说明之前已经在桶中获取了节点,那桶怎么回事空的呢?????)
        if ((next = (current = e).next) == null && (t = table) != null) {
        	// 上面的next获取失败,这里使用切换槽位的方式寻找下一个next
            do {} while (index < t.length && (next = t[index++]) == null);
        }
        return e;
    }

    public final void remove() {
        Node<K,V> p = current;
        if (p == null)
            throw new IllegalStateException();
        // fast-fail
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
        // 删除当前迭代其中的current
        current = null;
        // 获取key
        K key = p.key;
        // 调用父级删除方法
        // 这里设置了movable为false,删除后不移动节点.这个值只对treeNode生效,去要考证设置成false的作用
        // 貌似是在迭代器中被设置了false
        removeNode(hash(key), key, null, false, false);
        // 更新计数
        expectedModCount = modCount;
    }
}
```
## `KeyIterator`
### 描述:
`HashIterator`的实现.包装了其中的`nextNode`方法,返回`Node`的**key**
### 代码:
```java
final class KeyIterator extends HashIterator
    implements Iterator<K> {
    public final K next() { return nextNode().key; }
}
```
## `ValueIterator`
### 描述:
`HashIterator`的实现.包装了其中的`nextNode`方法,返回`Node`的**value**
### 代码:
```java
final class ValueIterator extends HashIterator
    implements Iterator<V> {
    public final V next() { return nextNode().value; }
}
```
## `EntryIterator`
### 描述:
`HashIterator`的实现.包装了其中的`nextNode`方法,直接返回了`Node`
### 代码:
```java
final class EntryIterator extends HashIterator
    implements Iterator<Map.Entry<K,V>> {
    public final Map.Entry<K,V> next() { return nextNode(); }
}
```
## `KeySet`
### 描述:
继承了`AbstractSet`,并通过内部类的特性,使实现方法通过直接调用父类`HashMap`的引用完成完成
### 代码:
```java
final class KeySet extends AbstractSet<K> {
	/**
	* 返回hashMap的成员变量size
    * @return 
    */
    public final int size()                 { return size; }
    /**
    * 因为是同名方法,所以只能使用类名.this.MethodName()的方式调用了
    */
    public final void clear()               { HashMap.this.clear(); }
    /**
    * 返回一个内部类key迭代器
    * @return 
    */
    public final Iterator<K> iterator()     { return new KeyIterator(); }
    /**
    * 调用父类方法
    * @param o
    * @return 
    */
    public final boolean contains(Object o) { return containsKey(o); }
    /**
    * 调用父类方法.标记不需要匹配值,删除后重建
    * @param key
    * @return 
    */
    public final boolean remove(Object key) {
        return removeNode(hash(key), key, null, false, true) != null;
    }
    /**
    * 返回一个内部类keySpl迭代器
    * @return 
    */
    public final Spliterator<K> spliterator() {
        return new KeySpliterator<>(HashMap.this, 0, -1, 0, 0);
    }
    /**
    * 实现forEach
    * 这里有个需要注意的地方
    * 对于fail-fast,这个方法会在所有元素迭代完成之后进行,才进行判断
    * @param action
    */
    public final void forEach(Consumer<? super K> action) {
        Node<K,V>[] tab;
        if (action == null)
            throw new NullPointerException();
        if (size > 0 && (tab = table) != null) {
            int mc = modCount;
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K,V> e = tab[i]; e != null; e = e.next)
                    action.accept(e.key);
            }
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }
}
```