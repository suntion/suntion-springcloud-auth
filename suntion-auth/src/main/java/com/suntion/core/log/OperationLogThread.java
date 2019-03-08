package com.suntion.core.log;

/*
    操作日志处理线程
 */
public class OperationLogThread implements Runnable{
    private OperationLogDTO operationLogDTO;

    /**
     * @param operationLogDTO 操作对象
     */
    public OperationLogThread(OperationLogDTO operationLogDTO) {
        this.operationLogDTO = operationLogDTO;
    }

    @Override
    public void run() {
        System.out.println("操作日志>" + this.operationLogDTO.getMsg());
    }
}
