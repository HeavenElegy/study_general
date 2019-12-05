## 描述
* 可以按照添**加元素的顺序**对元素进行迭代的`HashMap`的子类.
* 注意,上面说的是**加元素的顺序**.也就是说,**更新元素**时,是不会影响遍历结构的的.除非设置参数`accessOrder`为`true`,将更新元素放置到**队末**.
* 这个类没有对其父类`HashMap`进行过多重写.主要通过实现`afterNode*`相关方法,在数据结构变更后,进行后置的`链表`结构更新进行维护.

# 常用与关键方法
## `linkNodeLast`方法
### 描述:
* 负责初始化成员变量`head`与`tail`.
* 当`head`与`tail`初始化完成后,负责将目标元素`p`连接到`tail`并更新原有`tail`到目标元素`p`  

### 代码:
```java    
private void linkNodeLast(LinkedHashMap.Entry<K,V> p) {
	// 缓存尾部
    LinkedHashMap.Entry<K,V> last = tail;
    // 更新尾部到新元素
    tail = p;
    // 判断老尾部是否已经初始化
    if (last == null)
    	// 老尾部为初始化,代表头部也没初始化.进行初始化操作
        head = p;
    else {
    	// 初始化以完成,将p链接到老尾部之后
        p.before = last;
        last.after = p;
    }
}
```
### `transferLinks`方法
### 描述:
使用`dst`替换`src`在双向链表中的位置
### 代码:
```java
private void transferLinks(LinkedHashMap.Entry<K,V> src,
                           LinkedHashMap.Entry<K,V> dst) {
	// 同步before,同时保存到局部变量
    LinkedHashMap.Entry<K,V> b = dst.before = src.before;
	// 同步after,同时保存到局部变量
    LinkedHashMap.Entry<K,V> a = dst.after = src.after;
    // 检查before
    if (b == null)
    	// 没有before.将dst设置为head节点
        head = dst;
    else
    	// 有before,将before与dst关联
        b.after = dst;
    // 检查after
    if (a == null)
    	// 没有after,将dst作为tail节点
        tail = dst;
    else
    	// 有after,将after与dst连接
        a.before = dst;
}
```
## `newNode`方法
### 描述:
重写了父类`newNode`方法.扩展双向链表的连接操作.返回了`HashMap.Node`的子类节点`LinkedHashMap.Entry`.
### 代码:
```java
Node<K,V> newNode(int hash, K key, V value, Node<K,V> e) {
    LinkedHashMap.Entry<K,V> p =
        new LinkedHashMap.Entry<K,V>(hash, key, value, e);
    // 创建的新节点.直接链接到末端节点上
    linkNodeLast(p);
    return p;
}
```
## `replacementNode`方法
### 描述:
扩展双向链表替换节点的操作.这个方法用于父类`HashMap`将`HashMap.TreeNode`替换为`HashMap.Node`时调用,这里进行了重写,使用带有双向链表的`LinkedHashMap.Entry`作为返回值  
注意: 这里`HashMap.TreeNode`是实现了`LinkedHashMap.Entry`的.也就是参数`p`,他可以直接强转为实现类`LinkedHashMap.Entry`
### 代码:
```java
Node<K,V> replacementNode(Node<K,V> p, Node<K,V> next) {
    LinkedHashMap.Entry<K,V> q = (LinkedHashMap.Entry<K,V>)p;
    LinkedHashMap.Entry<K,V> t =
        new LinkedHashMap.Entry<K,V>(q.hash, q.key, q.value, next);
    // 替换节点
    transferLinks(q, t);
    return t;
}
```
## `newTreeNode`方法
### 描述:
重写了父类方法`newTreeNode`.扩展双向链表的连接操作.同样,因为`HashMap.TreeNode`实现`LinkedHashMap.Entry`.可以直接通过`linkNodeLast`方法进行连接操作
### 代码:
```java
TreeNode<K,V> newTreeNode(int hash, K key, V value, Node<K,V> next) {
    TreeNode<K,V> p = new TreeNode<K,V>(hash, key, value, next);
    linkNodeLast(p);
    return p;
}
```
## `replacementTreeNode`方法
### 描述:
同`replacementNode`.扩展双向链表替换节点的操作.只是节点类型变成了`TreeNode`.又因为他是`LinkedHashMap.Entry`的子类,可以直接交给`transferLinks`使用.进行双向链表替换操作
### 代码:
```java
TreeNode<K,V> replacementTreeNode(Node<K,V> p, Node<K,V> next) {
    LinkedHashMap.Entry<K,V> q = (LinkedHashMap.Entry<K,V>)p;
    TreeNode<K,V> t = new TreeNode<K,V>(q.hash, q.key, q.value, next);
    transferLinks(q, t);
    return t;
}
```
## `afterNodeRemoval`方法
### 描述:
删除节点后调用.进行双向链表同步
### 代码:
```java
void afterNodeRemoval(Node<K,V> e) { // unlink
	// b - before节点
	// p - 被删除节点
	// a - after节点
    LinkedHashMap.Entry<K,V> p =
        (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
    // 清除p的双端引用
    p.before = p.after = null;
    
    // 判断before是否存在
    if (b == null)
    	// 没有before
    	// 设置a为head
        head = a;
    else
    	// 存在before
    	// 连接b->a.注意,这是单向连接,现在还无法确认a是否存在.如果a为空,b就是链表中的唯一节点.after属性为null
        b.after = a;
    // 判断a是否为空
    if (a == null)
    	// a为空
    	// tail设置为b
        tail = b;
    else
    	// a存在
    	// 连接 a->b.注意,这里也是单向连接.如果b是空的话,a现在就是head且before属性是null
        a.before = b;
}
```
## `afterNodeAccess`方法
### 描述:
更新节点后调用.进行双向链表同步
### 代码:
```java
void afterNodeAccess(Node<K,V> e) { // move node to last
	// oldTail.老尾部缓存
    LinkedHashMap.Entry<K,V> last;
    // 判断accessOrder.即按照访问(更新)顺序排列
    // 获取老尾部
    // 判断当前元素是不是尾部元素
    if (accessOrder && (last = tail) != e) {
    	// accessOrder==true且e不要尾部元素
    	
    	// b - fefore
    	// p - 当前元素
    	// a - after
        LinkedHashMap.Entry<K,V> p =
            (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
        
        // 因为p将变为尾部元素,所以直接设置p.after为null.
        p.after = null;
        
        // 判断b
        if (b == null)
        	// b为null,p节点就是head节点
        	// a作为头部节点
            head = a;
        else
        	// b不为空
        	// 连接b->a. 注意,这里是单向连接.a可能为null,a.before的连接交给后续判断
            b.after = a;
        
        // 判断a
        if (a != null)
        	// a不为空
        	// a->b.注意,这里是单向链接.b可能是null.b.after的连接交给后续判断
            a.before = b;
        else
        	// a为空.p节点就是tail节点
        	// 这里有两个分支,需要判断b是否为空.此处a已经为空,如果b也为空,说明p是列表中的唯一节点.这个判断委托到后续判断中处理
        	// 此时,last变量已经失去意义,它与p为同一对象.
        	// 这里说一下赋值last = b;的作用.注意,这是本人猜测!
        	// 是为了统一算法的外在样式.因为变量last在在本方法中是不会为空的,且在所有的情形中,都会调用p.before = last;last.after = p;进行连接(除了p是唯一元素的情况).
        	// 那么在b存在的时候,再次与p进行连接,在链表结构上也是没有问题的,统一被视作被操作元素的前一个元素
            last = b;
        if (last == null)
        	// p是唯一元素
            head = p;
        else {
        	// 连接到尾部节点
            p.before = last;
            last.after = p;
        }
        // 更新尾部节点到p
        tail = p;
        // 修改计数++
        ++modCount;
    }
}
```
# 内部类
## `LinkedHashIterator`
### 描述:
封装了针对链表结构的迭代器.并向子类提供了共有的扩展方法.
### 代码:
```java
abstract class LinkedHashIterator {
    LinkedHashMap.Entry<K,V> next;
    LinkedHashMap.Entry<K,V> current;
    int expectedModCount;

    LinkedHashIterator() {
    	// 初始化next节点为当前head
        next = head;
        expectedModCount = modCount;
        current = null;
    }

    public final boolean hasNext() {
        return next != null;
    }

    final LinkedHashMap.Entry<K,V> nextNode() {
    	// 缓存next
        LinkedHashMap.Entry<K,V> e = next;
        // fast-fail
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
        // next为空
        if (e == null)
            throw new NoSuchElementException();
        // 设置当前
        current = e;
        // 更新next到下一个
        next = e.after;
        return e;
    }

    public final void remove() {
    	// 获取当前
        Node<K,V> p = current;
        // null判断
        if (p == null)
            throw new IllegalStateException();
        // fast-fail
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
        // 迭代器置空
        current = null;
        // 获取key
        K key = p.key;
        // 调用父类的removeNode方法进行节点删除
        removeNode(hash(key), key, null, false, false);
        // 同步更新计数
        expectedModCount = modCount;
    }
}
```
# 内部类
## `LinkedHashIterator实现`
### 描述:
分别继承了`LinkedHashIterator`并使用前者的`nextNode`方法返回不同数据
### 代码:
```java
final class LinkedKeyIterator extends LinkedHashIterator
    implements Iterator<K> {
    public final K next() { return nextNode().getKey(); }
}

final class LinkedValueIterator extends LinkedHashIterator
    implements Iterator<V> {
    public final V next() { return nextNode().value; }
}

final class LinkedEntryIterator extends LinkedHashIterator
    implements Iterator<Map.Entry<K,V>> {
    public final Map.Entry<K,V> next() { return nextNode(); }
}
```