# 八种排序算法原理及Java实现
## 1. 概述

排序算法分为内部排序和外部排序，内部排序把数据记录放在内存中进行排序，而外部排序因排序的数据量大，内存不能一次容纳全部的排序记录，所以在排序过程中需要访问外存。
![image](https://user-gold-cdn.xitu.io/2018/9/10/165c15fba21994d3?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)
<!-- GFM-TOC -->
- [一、冒泡排序](##3.快速排序)
- [二、快速排序](##3.快速排序)
- [三、堆排序](##3.快速排序)
- [四、选择排序](##3.快速排序)
- [五、归并排序](##3.快速排序)
<!-- GFM-TOC -->

## 2. 冒泡排序
### 2.1 基本思想
冒泡排序（Bubble Sort）是一种简单的排序算法。它重复访问要排序的数列，一次比较两个元素，如果他们的顺序错误就把他们交换过来。访问数列的工作是重复地进行直到没有再需要交换的数据，也就是说该数列已经排序完成。这个算法的名字由来是因为越小的元素会经由交换慢慢“浮”到数列的顶端，像水中的气泡从水底浮到水面。

![image](https://user-gold-cdn.xitu.io/2018/9/10/165c1650231bab74?imageslim)
### 2.2 算法描述
冒泡排序算法的算法过程如下：
①. 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
②. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
③. 针对所有的元素重复以上的步骤，除了最后一个。
④. 持续每次对越来越少的元素重复上面的步骤①~③，直到没有任何一对数字需要比较。
### 2.3 代码实现
 
```
public static void sort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        int length = array.length;
        //外层：需要length-1次循环比较
        for (int i = 0; i < length - 1; i++) {
            //内层：每次循环需要两两比较的次数，每次比较后，都会将当前最大的数放到最后位置，所以每次比较次数递减一次
            for (int j = 0; j < length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    //交换数组array的j和j+1位置的数据
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
```
## 3. 快速排序
### 3.1 基本思想
快速排序（Quicksort）是对冒泡排序的一种改进，借用了分治的思想，由C. A. R. Hoare在1962年提出。它的基本思想是：通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
### 3.2 算法描述
快速排序使用分治策略来把一个序列（list）分为两个子序列（sub-lists）。步骤为：
1. 从数列中挑出一个元素，称为”基准”（pivot）。
1. 重新排序数列，所有比基准值小的元素摆放在基准前面，所有比基准值大的元素摆在基准后面（相同的数可以到任一边）。在这个分区结束之后，该基准就处于数列的中间位置。这个称为分区（partition）操作。
1. 递归地（recursively）把小于基准值元素的子数列和大于基准值元素的子数列排序。
1. 递归到最底部时，数列的大小是零或一，也就是已经排序好了。这个算法一定会结束，因为在每次的迭代（iteration）中，它至少会把一个元素摆到它最后的位置去。

![image](https://user-gold-cdn.xitu.io/2018/9/10/165c220dad2f209c?imageslim)

### 3.3 代码实现
#### ①. 挖坑法
用伪代码描述如下：
- （1）low = L; high = R; 将基准数挖出形成第一个坑a[low]。
- （2）high--，由后向前找比它小的数，找到后挖出此数填前一个坑a[low]中。
- （3）low++，由前向后找比它大的数，找到后也挖出此数填到前一个坑a[high]中。
- （4）再重复执行②，③二步，直到low==high，将基准数填入a[low]中。
##### 举例说明：
一个无序数组：[4, 3, 7, 5, 10, 9, 1, 6, 8, 2]
- （1）随便先挖个坑，就在第一个元素（基准元素）挖坑，挖出来的“萝卜”（第一个元素4）在“篮子”（临时变量）里备用。
- 挖完之后的数组是这样：[ ***==坑==***, 3, 7, 5, 10, 9, 1, 6, 8,2]
- （2）挖右坑填左坑：从右边开始，找个比“萝卜”（元素4）小的元素，挖出来，填到前一个坑里面。
- 填坑之后：[ 2, 3, 7, 5, 10, 9, 1, 6, 8,***==坑==***]
- （3）挖左坑填右坑：从左边开始，找个比“萝卜”（元素4）大的元素，挖出来，填到右边的坑里面。
- 填坑之后：[ 2, 3,***==坑==***, 5, 10, 9, 1, 6, 8, 7]
- （4）挖右坑填左坑：从右边开始，找个比“萝卜”（元素4）小的元素，挖出来，填到前一个坑里面。
- 填坑之后：[ 2, 3, 1, 5, 10, 9,***==坑==***, 6, 8, 7]
- （5）挖左坑填右坑：从左边开始，找个比“萝卜”（元素4）大的元素，挖出来，填到右边的坑里面。
- 填坑之后：[ 2, 3, 1,***==坑==***, 10, 9, 5, 6, 8, 7]
- （6）挖右坑填左坑：从右边开始，找个比“萝卜”（元素4）小的元素，挖出来，填到前一个坑里面，这一次找坑的过程中，找到了上一次挖的坑了，说明可以停了，用篮子里的的萝卜，把这个坑填了就行了，并且返回这个坑的位置，作为分而治之的中轴线。
- 填坑之后：[ 2, 3, 1,***==4==***, 10, 9, 5, 6, 8, 7]
上面的步骤中，第2，4, 6其实都是一样的操作，3和5的操作也是一样的，代码如下：


```
public static void sort(int arr[], int low, int high) {
        if (arr == null || arr.length <= 0) {
            return;
        }
        if (low >= high) {
            return;
        }

        int left = low;
        int right = high;
        int temp = arr[left]; //保存基准的值 坑1

        while (left < right) {
            while (left < right && arr[right] >= temp) {// 获取基准值右边的小于基准值的下标
                right--;
            }
            arr[left] = arr[right]; //  从后向前找到比基准小的元素，插入到基准位置坑1中，原来的right下标则重复出现了，此处列为坑2

            while (left < right && arr[left] <= temp) { // 获取基准值左边边的大于基准值的下标
                left ++;
            }
            arr[right] = arr[left]; // 从前往后找到比基准大的元素，放到刚才挖的坑2中，此处的left下标列为坑3
        }
        arr[left] = temp; //基准值填补到坑3中，准备分治递归快排
        sort(arr, low, left-1);
        sort(arr, left + 1, high);
    }
```
## 4.堆排序
### 1、算法思想

1. 将数组构建成大堆二叉树，即所有节点的父节点的值都大于叶子节点的完全二叉树
2. 若叶子节点比父节点大，则交换位置
3. 根节点即为最大值，则将根节点与最后的的一个叶子节点交换位置
4. 重复1，2操作，每次都找最大值则放置最后即可排序完成 

![image](https://user-gold-cdn.xitu.io/2018/6/2/163bedb0f755a69d?imageslim)

此处难以理解，先加进来后续研究

## 4. 直接插入排序
### 4.1 基本思想

直接插入排序的基本思想是：将数组中的所有元素依次跟前面已经排好的元素相比较，如果选择的元素比已排序的元素小，则交换，直到全部元素都比较过为止。
![image](https://user-gold-cdn.xitu.io/2018/9/10/165c25fe0f393246?imageslim)
![image](https://user-gold-cdn.xitu.io/2018/9/10/165c260584694ff9?imageslim)

### 4.3 代码实现

提供两种写法，一种是移位法，一种是交换法。移位法是完全按照以上算法描述实，再插入过程中将有序序列中比待插入数字大的数据向后移动，由于移动时会覆盖待插入数据，所以需要额外的临时变量保存待插入数据，代码实现如下：

```
public static void sort(int[] a) {
        if (a == null || a.length == 0) {
            return;
        }

        for (int i = 1; i < a.length; i++) {
            int j = i - 1;
            int temp = a[i]; // 先取出待插入数据保存，因为向后移位过程中会把覆盖掉待插入数
            while (j >= 0 && a[j] > a[i]) { // 如果待是比待插入数据大，就后移
                a[j+1] = a[j];
                j--;
            }
            a[j+1] = temp; // 找到比待插入数据小的位置，将待插入数据插入
        }
    }
```
而交换法不需求额外的保存待插入数据，通过不停的向前交换带插入数据，类似冒泡法，直到找到比它小的值，也就是待插入数据找到了自己的位置。

```
public static void sort2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        for (int i = 1; i < arr.length; i ++) {
            int j = i - 1;
            while (j >= 0 && arr[j] > arr[i]) {
                arr[j + 1] = arr[j] + arr[j+1];      //只要大就交换操作
                arr[j] = arr[j + 1] - arr[j];
                arr[j + 1] = arr[j + 1] - arr[j];
                System.out.println("Sorting:  " + Arrays.toString(arr));
            }
        }
    }
```

## 6.选择排序
### 6.1 基本思想
在未排序序列中找到最小（大）元素，存放到未排序序列的起始位置。在所有的完全依靠交换去移动元素的排序方法中，选择排序属于非常好的一种。
### 6.2 算法描述
- ①. 从待排序序列中，找到关键字最小的元素；
- ②. 如果最小元素不是待排序序列的第一个元素，将其和第一个元素互换；
- ③. 从余下的 N - 1 个元素中，找出关键字最小的元素，重复①、②步，直到排序结束。
  
![image](https://user-gold-cdn.xitu.io/2018/9/10/165c2d4fd254df47?imageslim)

### 6.3 代码实现

```
public class SelectSort {
    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i; // 默认0
            for (int j = i+1; j < arr.length; j ++) { //选出之后待排序中值最小的位置
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (min != i) {
                arr[min] = arr[i] + arr[min];
                arr[i] = arr[min] - arr[i];
                arr[min] = arr[min] - arr[i];
            }
        }
    }

```
## 7.归并排序
归并排序是建立在归并操作上的一种有效的排序算法，1945年由约翰·冯·诺伊曼首次提出。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用，且各层分治递归可以同时进行。
### 7.1 基本思想
归并排序算法是将两个（或两个以上）有序表合并成一个新的有序表，即把待排序序列分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并为整体有序序列。
 ![image](https://user-gold-cdn.xitu.io/2018/9/10/165c2d849cf3a4b6?imageslim)
###  7.2 算法描述
采用递归法：
- ①. 将序列每相邻两个数字进行归并操作，形成 floor(n/2)个序列，排序后每个序列包含两个元素；
- ②. 将上述序列再次归并，形成 floor(n/4)个序列，每个序列包含四个元素；
- ③. 重复步骤②，直到所有元素排序完毕
 ![image](https://user-gold-cdn.xitu.io/2018/9/10/165c2d88eb326ec1?imageslim)
### 7.3 代码实现

 
```
public class MergeSort {

    public static int[] sort(int [] a) {
        if (a.length <= 1) {
            return a;
        }
        int num = a.length >> 1;
        int[] left = Arrays.copyOfRange(a, 0, num);
        int[] right = Arrays.copyOfRange(a, num, a.length);
        return mergeTwoArray(sort(left), sort(right));
    }

    public static int[] mergeTwoArray(int[] a, int[] b) {
        int i = 0, j = 0, k = 0;
        int[] result = new int[a.length + b.length]; // 申请额外空间保存归并之后数据

        while (i < a.length && j < b.length) { //选取两个序列中的较小值放入新数组
            if (a[i] <= b[j]) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }

        while (i < a.length) { //序列a中多余的元素移入新数组
            result[k++] = a[i++];
        }
        while (j < b.length) {//序列b中多余的元素移入新数组
            result[k++] = b[j++];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] b = {3, 1, 5, 4};
        System.out.println(Arrays.toString(sort(b)));
    }
} 
```
