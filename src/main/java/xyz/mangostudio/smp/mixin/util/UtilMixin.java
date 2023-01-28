package xyz.mangostudio.smp.mixin.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.MoreExecutors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

@Mixin(Util.class)
public abstract class UtilMixin {
    @Shadow
    @Final
    private static AtomicInteger NEXT_WORKER_ID;

    @Shadow
    private static void uncaughtExceptionHandler(Thread thread, Throwable throwable) {
        throw new AssertionError();
    }

    @Shadow
    private static int getMaxBackgroundThreads() {
        throw new AssertionError();
    }

    /**
     * @author PaperMC (Improve-Server-Thread-Pool-and-Thread-Priorities.patch).
     * @reason Use a simple executor since Fork join is a much more complex pool type.
     */
    @Overwrite
    private static ExecutorService createWorker(String serviceName) {
        int i = MathHelper.clamp(Runtime.getRuntime().availableProcessors() - 1, 1, UtilMixin.getMaxBackgroundThreads());

        if (i <= 0) {
            return MoreExecutors.newDirectExecutorService();
        } else {
            if (serviceName.equals("Bootstrap")) i = 1;

            return new ThreadPoolExecutor(i, i, 0L, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(), target -> {
                Thread thread = new Thread(target, "Worker " + serviceName + "-" + NEXT_WORKER_ID.getAndIncrement());
                thread.setPriority(Thread.NORM_PRIORITY - 2);
                thread.setUncaughtExceptionHandler(UtilMixin::uncaughtExceptionHandler);

                return thread;
            });
        }
    }

    /**
     * @reason IOThread is not used much in server-side. So we de-priority it.
     */
    @Inject(method = "method_27956(Ljava/lang/Runnable;)Ljava/lang/Thread;", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void opt$modifyIoThreadPriority(Runnable runnable, CallbackInfoReturnable<Thread> cir, Thread thread) {
        thread.setDaemon(true);
        thread.setPriority(Thread.NORM_PRIORITY - 2);
    }
}
