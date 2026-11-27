package com.plm.project;

import org.apache.fesod.sheet.FesodSheet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ProjectManageApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("hello world");
    }

    @Test
    void testTask(){
//        int[] servers = new int[]{5,1,4,3,2};
//        int[] tasks = new int[]{2,1,2,4,5,2,1};
//        int[] ints = assignTasks(servers, tasks);
//        System.out.println("结果--------------------"+Arrays.toString(ints));
        long l = minimumCost(388168, 988887, 336014, 50411, 252237);
        System.out.println(l);
    }

    public int[] assignTasks(int[] servers, int[] tasks) {
        //需要一个时间坐标，来判断此刻哪些任务可以开始处理，服务器是否空闲等
        long time = 0;
        //把服务器排序，按照权重，权重相同按照下标，即权重由小到大，权重一样，下标由小到大。
        int n = servers.length;
        Integer[] ids = new Integer[n];
        for(int i=0;i<n;i++) ids[i]=i;
        Arrays.sort(ids,(a,b)->{
            int c = Integer.compare(servers[a],servers[b]);
            return c!=0?c:Integer.compare(a,b);
        });
        //用map容器存放每个服务器的空闲时间
        Map<Integer,Long> map = new HashMap<>();
        for(Integer id:ids) {map.put(id,0L);}
        //初始化ans
        int m = tasks.length;
        int[] ans = new int[m];
        //遍历任务
        for(int i=0;i<m;i++){
            //判断此刻是否能够处理该任务
            time = Math.max(time, i);
            //查找空闲服务器，找不到就快进时间
            Object[] idle = findIdle(ids,time,map);
            time = Long.parseLong(idle[1].toString());
            int idleId = Integer.parseInt(idle[0].toString());
            //更新服务器的结束时间
            map.put(idleId,time+tasks[i]);
            //把服务器下标加到ans里
            ans[i] = idleId;
        }
        return ans;

    }

    //查找空闲服务器
    public Object[] findIdle(Integer[] ids,Long time,Map<Integer,Long> map) {
        Long min = map.get(ids[0]);
        Integer minId = ids[0];
        String s = new String();
        s.length();
        StringBuffer sb = new StringBuffer();
        for(Integer id:ids){
            if(time > map.get(id)){
                return new Object[]{id,map.get(id)};
            }
            if(min>map.get(id)){
                min = map.get(id);
                minId = id;
            }
        }
        return new Object[]{minId,min};
    }

    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        //如果cost1+cost2>both，就优先用both，否则优先用cost1+cost2，试试这个思路
        long sum = 0;
        if(cost1+cost2>costBoth){
            if(need1>need2){
                sum = (long)costBoth * need2;
                int min = Math.min(cost1,costBoth);
                sum = sum +(long) min * (need1-need2);
            }else{
                sum = (long)costBoth * need1;
                int min = Math.min(cost2,costBoth);
                sum = sum + (long)min * (need2-need1);
            }
        }else{
            sum = (long)cost1*need1 + (long)cost2*need2;
        }
        return sum;
    }

    // 在某个测试类里直接跑
    @Test
    public void testRead() {
        String fileName =  "C:\\Users\\简单的蛋\\Desktop\\工作簿1.xlsx";
        // 使用 Map 接收，不依赖你写的实体类
        List<Map<Integer, String>> list = FesodSheet.read(fileName)
                .headRowNumber(0)
                .sheet()
                .doReadSync(); // 同步读取

        for (Map<Integer, String> map : list) {
            System.out.println(map); // 看看这里能不能打印出数据
        }
    }

}
