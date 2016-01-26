package com.stevenkcolin.xiaobaiju;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.SystemClock;
import android.test.InstrumentationTestCase;
import android.widget.Button;

import com.stevenkcolin.xiaobaiju.activity.TaskDetailActivity;
import com.stevenkcolin.xiaobaiju.activity.TaskListActivity;

/**
 * Created by linchen on 1/21/16.
 */
public class ExampleTest extends InstrumentationTestCase {
    private TaskListActivity app = null;
    private TaskDetailActivity detailActivity = null;
    private Button mbtnAddTask = null;


    @Override
    protected void setUp() {
        try {

            super.setUp();

        } catch (Exception e) {
            e.printStackTrace();
        }
//        Intent intent = new Intent();
//        //System.out.println("---------------------------------------------------");
//        intent.setClassName("com.stevenkcolin.xiaobaiju", TaskListActivity.class.getName());
//        System.out.println("99999999999999999");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        //获取被测对象context
//        app = (TaskListActivity) getInstrumentation().startActivitySync(intent);
//
//        //System.out.println("Apppppppppp:  "+app);
//
//        //获取被测试的程序的资源要加包名,不然无法获取
//        mbtnAddTask = (Button)app.findViewById(R.id.add_task);
        //task_title = (EditText)detailActivity.findViewById(R.id.task_title);
        //task_completed = (CheckBox)detailActivity.findViewById(R.id.task_completed);

    }

    /*
     * 垃圾清理与资源回收 ,测试用例完成时调用
     *
     */
    @Override
    protected void tearDown() {
        //app.finish();
        try {
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void test() throws Exception {
        final int expected = 1;
        final int reality = 1;
        //assertEquals(1, 1);
        assertEquals(expected, reality);
    }

    public void testAction() throws Exception {
        Instrumentation instrumentation = getInstrumentation();

        // Register we are interested in the authentication activiry...
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(TaskListActivity.class.getName(), null, false);

        // Start the authentication activity as the first activity...
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(instrumentation.getTargetContext(), TaskListActivity.class.getName());
        instrumentation.startActivitySync(intent);

        // Wait for it to start...
        SystemClock.sleep(3000);
//        Activity currentActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5);
//        View currentView = currentActivity.findViewById(R.id.add_task);
//        TouchUtils.clickView(this, currentView);
//
//        instrumentation.removeMonitor(monitor);
//        monitor = instrumentation.addMonitor(TaskDetailActivity.class.getName(), null, false);
//        currentActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5);
//
//        View currentView2 = currentActivity.findViewById(R.id.task_title);
//        TouchUtils.clickView(this, currentView2);




        SystemClock.sleep(3000);


    }

    /*
     * 活动功能测试
     */
    public void testActivity() throws Exception {
//        Instrumentation instrumentation = getInstrumentation();
//        Log.e("report11111", TaskDetailActivity.class.getName());
//        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(TaskListActivity.class.getName(), null, false);
//
//        Log.v("testActivity", "test the Activity");
//        SystemClock.sleep(1500);
//        getInstrumentation().runOnMainSync(new PerformClick(mbtnAddTask));
//        SystemClock.sleep(3000);
//
//
//        Activity currentActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5);
//
//        Log.e("test11111",currentActivity.getLocalClassName()+"******"+currentActivity.getPackageName());
//
//        TextView task_title= (TextView) currentActivity.findViewById(R.id.task_title);
//        CheckBox task_completed = (CheckBox) currentActivity.findViewById(R.id.task_completed);
//        task_completed.setChecked(true);
//        SystemClock.sleep(3000);
//        task_title.setText("test11111");
//        SystemClock.sleep(3000);
    }

    /*
     *模拟按钮点击的接口
     */
    private class PerformClick implements Runnable {
        Button btn;

        public PerformClick(Button button) {
            btn = button;
        }
        public void run() {
            btn.performClick();
        }
    }
}
