package org.example.core.control;

import org.example.log.ChopperLogFactory;
import org.example.log.LoggerType;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;

/**
 * @author Genius
 * @date 2023/07/21 10:22
 **/

/**
 * 热门模块的抽象类，它包含了任务完成状态以及失败成功日志，以及后续数据的获取和返回
 * @param <T>
 */
public abstract class HotModuleLoadTask<T> implements LoadTask<T>{
    public enum FinishFlag{
        FINISH,NOT_FINISH,FAIL
    }
    private FinishFlag finishFlag = FinishFlag.NOT_FINISH;


    public T start() {
        clearFinishFlag();
        return this.start0();
    }

    protected abstract T start0();
    protected void fail(Exception e){
        finishFlag = FinishFlag.FAIL;
        ChopperLogFactory.getLogger(LoggerType.Hot).error("loadTask{} finish fail Error:{}",this.getClass().getName(),e.getMessage());
    }

    protected void success(){
        finishFlag = FinishFlag.FINISH;
    }

    protected T getData(Spider spider,String url){
        T data = ((ResultItems) spider.get(url)).get("data");
        spider.close();
        return data;
    }

    public FinishFlag isFinish(){
        return finishFlag;
    }

    public void clearFinishFlag(){finishFlag = FinishFlag.NOT_FINISH;}

    @Override
    public void end() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getCacheSize() {
        return 0;
    }

    @Override
    public int flushCacheAndSave(String key) {
        return 0;
    }
}