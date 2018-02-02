package com.fewok.dsl.bpm.demo;

import lombok.Data;

/**
 * 工作流
 * 参考 BPMN规范
 * <p>
 * 流对象（Flow Object）
 * -- 事件（Events），活动（Activities），关口（Gateways）
 * 连接对象（Connecting Objects）
 * -- 顺序流（Sequence Flow），消息流（Message Flow），关联（Association）
 * 泳道（Swimlanes）
 * -- 池（Pool），道（Lane）
 * 器物（Artifacts/Artefacts）
 * -- 数据对象（Data Object），组（Group），注释（Annotation）
 * <p>
 * @author notreami on 17/11/20.
 */
public class Workflow {

    /**
     * 流程引擎
     */
    @Data
    public static class ProcessEngine {
        private ProcessInstance processInstance;

        public static RuntimeService getRuntimeService() {
            return new RuntimeService();
        }
    }

    @Data
    public static class RuntimeService {
        public ProcessInstance startProcessInstanceByKey() {
            return new ProcessInstance();
        }
    }


    /**
     * 流程实例
     */
    @Data
    public static class ProcessInstance {
    }

    /**
     * 流处理
     */
    @Data
    public static class Flow {
        private Activities activities;//活动
        private Gateways gateways;//关口
        private Events events;//事件

        @Data
        public static class Activities {
            private String activity;//activity的类型
            private String task;//任务代表单一工作单元，它不会或不能被分解为更深层次上的业务流程细节，而不包含操作程序步骤的图示（此非BPMN的目的）。
            private String subProcess;//call Activity  //用于隐藏或显露深层业务流程细节——收起时，在矩形底部用加号标明子流程；展开时，在矩形内显示全部的流对象、连接对象及器物。
            private String transaction;//事务子流程 子流程的一种形式，其所包含的全部活动必须作为一个整体对待，即它们必须完全结束以满足目标，其中任何一个失败就必须全部偿还（撤回）。事务作为扩展的子流程，用双线环绕。
            private String adHocSubProcess;//点对点
            private String loopActivity;//循环
            private String multipleInstancesActivity;//多个实例
        }

        @Data
        public static class Task {
            private String serviceTask;//服务任务
            private String sendTask;//发送任务
            private String pushTask;//推送任务
            private String receiveTask;//状态任务，一般表示活动状态，需signal进行转换
            private String userTask;//人机交互任务
            private String manualTask;//线下手工执行任务
            private String businessRuleTask;//业务规则任务
            private String wrapperTask;//组包任务
            private String scriptTask;//脚本任务
            private String abstractTask;//抽象任务
        }

        @Data
        public static class Gateways {
            private String parallelGateway;//并行执行网关
            private String exclusiveGateway;//排它执行网关
            private String inclusiveGateway;//包容执行网关
            private String eventBasedGateway;//事件执行网关：即到该网关流程会暂停，等待外部事件的通知
            private String dataBasedGateway;//数据执行网关：即到该网关流程会暂停，等待外部事件的通知
            private String complexGateway;//复杂执行网关：具有与包容网关相似的行为，但是可以根据特定业务场景，自定义路径拆分和合并算法
        }

        //Event是指业务流程的运行过程中发生的事件。这些event会影响到流程的状态。
        @Data
        public static class Events {
            private String startEvent;//起始事件
            private String intermediateEvent;//中间事件
            private String intermediateBoundaryEvent;//中间边界事件(如：超时，异常等)
            private String subProcessEvent;//子流程事件
            //            private String compensationEvent;//补偿事件
            private String endEvent;//结束事件


            private String conditionalEvent;//条件事件
            private String linkEvent;//链接事件
            private String multipleEvent;//多重事件
            private String parallelMultipleEvent;//并行多重事件
            private String errorEvent;//出错事件
            private String compensationEvent;//补偿事件
            private String cancelEvent;//取消事件
            private String escalationEvent;//升级事件
            private String commonEvent;//通用事件
        }
    }

    /**
     * 连接对象
     */
    @Data
    public static class Connections {
        private String sequenceFlow;//序列流。用来表示业务流程中被执行的业务单元的执行顺序。
        private String messageFlow;//消息流。用来表示不同业务流程参与者之间的消息交互的信息流。
        private String association;//关联。用来把对某个业务活动的输入输出的描述与这个业务活动联系起来。
        private String defaultFlow;
        private String conditionalFlow;
        private String link;
    }

    /**
     * 泳道
     */
    @Data
    public static class Swimlanes {
        private String pool;//池
        private String lane;//道
    }

    /**
     *
     */
    @Data
    public static class Artifacts {
        private String dataRequest;//请求数据
        private String dataFlow;//流程所需数据
        private String group;//流程分组
        private String description;//备注
    }

    @Data
    public static class Conversation{
        private String conversation;
        private String callConversation;
        private String subConversation;
    }

    @Data
    public static class Marker{
        private String subProcessMarker;
        private String loopMarker;
        private String parallelMIMarker;
        private String sequentMIMarker;
        private String adHocMarker;
        private String compenMarker;
    }

}
