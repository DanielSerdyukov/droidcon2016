-keepattributes Signature,Exceptions,InnerClasses,EnclosingMethod,*Annotation*

-keep public class moscow.droidcon.reddit.** {
    public static <fields>;
    public <methods>;
}

-dontwarn java.lang.invoke.*

-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**