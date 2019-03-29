

public class HelloWorld
{

    public static void main(String[] args) throws Exception
    {
        Thread thread = new Thread(new Runnable()
        {

            public void run()
            {
                try
                {
                    while (!Thread.currentThread().isInterrupted())
                    {
                        Thread.sleep(1);
                        System.out.println("Hello World!");
                    }
                } catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
        System.out.println("press enter to quit");
        System.in.read();
        thread.interrupt();
    }
}
