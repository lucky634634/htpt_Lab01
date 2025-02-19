public class Process {
    public ProcessInfo currentProcessInfo;

    public ProcessInfo[] processInfos;

    public Process(ProcessInfo currentProcessInfo, ProcessInfo[] processInfos) {
        this.currentProcessInfo = currentProcessInfo;
        this.processInfos = processInfos;
    }
}
