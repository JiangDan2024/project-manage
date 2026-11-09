package com.plm.project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ProjectManageApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("hello world");
    }

    @Test
    void testTask(){
        int[] servers = new int[]{5,1,4,3,2};
        int[] tasks = new int[]{2,1,2,4,5,2,1};
        int[] ints = assignTasks(servers, tasks);
        System.out.println("结果--------------------"+Arrays.toString(ints));
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

}
