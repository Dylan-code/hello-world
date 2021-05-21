package com.marx.common;



/**
 * 返回前端的数据类型
 * */
public class Result {
    /* 状态码 0表示失败 1表示成功 */
    private Integer code;
    /* 描述性信息 */
    private String msg;
    /* 返回操作的具体内容 */
    private Object resultContent;

    public Result(Integer code, String msg, Object resultContent) {
        this.code = code;
        this.msg = msg;
        this.resultContent = resultContent;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", resultContent=" + resultContent +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResultContent() {
        return resultContent;
    }

    public void setResultContent(Object resultContent) {
        this.resultContent = resultContent;
    }

    /* 定义一个通用的成功类 */
    public static Result success() {
        return success("成功");
    }

    /**
     * 自定义成功的结果类的信息
     *
     * @param msg 结果类描述性信息
     */
    public static Result success(String msg) {
        return success(msg, null);
    }

    /**
     * 自定义成功的结果类的信息
     *
     * @param msg 结果类描述性信息
     * @param resultContent 封装在Result结果类中的数据
     */
    public static Result success(String msg, Object resultContent) {
        return new Result(1, msg, resultContent);
    }

    /**
     * 定义一个通用的失败类
     */
    public static Result error() {
        return error("失败");
    }

    /**
     * 自定义失败的结果类的信息
     *
     * @param msg 结果类描述性信息
     */
    public static Result error(String msg) {
        return error(msg, null);
    }

    /**
     * 自定义成功的结果类的信息
     *
     * @param msg 结果类描述性信息
     * @param resultContent 封装在Result结果类中的数据
     */
    public static Result error(String msg, Object resultContent) {
        return new Result(0, msg, resultContent);
    }

    /**
     * 响应返回结果
     *
     * @param num 对表操作成功的记录数
     * @return 对应的结果类
     */
    public static Result toResult(Integer num) {
        if (num > 0) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 响应返回结果
     *
     * @param resultContent 对表操作的结果
     * @return 对应的结果类
     */
    public static Result toResult(Object resultContent) {
        if (resultContent == null) {
            return error("操作失败");
        } else {
            return success("操作成功", resultContent);
        }
    }

    /**
     * 响应返回结果
     *
     * @param result 操作结果
     * @return 返回结果
     */
    public static Result toResult(boolean result) {
        return result ? Result.success() : Result.error();
    }

}
