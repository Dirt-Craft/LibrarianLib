package com.teamwizardry.librarianlib.features.gui.value;

import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.LongSupplier;

public class IMValueLong implements GuiAnimatable {
    private Storage storage;

    private IMValueLong(Storage initialStorage) {
        this.storage = initialStorage;
    }
    public IMValueLong(long initialValue) {
        this.storage = new Storage.Fixed(initialValue);
    }
    public IMValueLong(LongSupplier initialCallback) {
        this.storage = new Storage.Callback(initialCallback);
    }

    /**
     * Gets the current value
     */
    public long get() {
        return storage.get();
    }

    /**
     * Sets the callback, unsetting the fixed value in the process
     */
    public void set(LongSupplier callback) {
        GuiAnimator.getCurrent().add(this);
        if(storage instanceof Storage.Callback) {
            ((Storage.Callback) storage).callback = callback;
        } else {
            storage = new Storage.Callback(callback);
        }
    }

    /**
     * Sets the fixed callback. This isn't often called as most classes will provide a delegated property to directly
     * access this value (`someProperty` will call longo `somePropery_im` for its value)
     */
    public void setValue(long value) {
        GuiAnimator.getCurrent().add(this);
        if(storage instanceof Storage.Fixed) {
            ((Storage.Fixed) storage).value = value;
        } else {
            storage = new Storage.Fixed(value);
        }
    }

    /**
     * A kotlin delegate method, used to allow properties to delegate to this IMValue (`var property by property_im`)
     */
    public long getValue(Object thisRef, KProperty property) {
        return storage.get();
    }

    /**
     * A kotlin delegate method, used to allow properties to delegate to this IMValue (`var property by property_im`)
     */
    public void setValue(Object thisRef, KProperty property, long value) {
        setValue(value);
    }

    /**
     * A kotlin helper to allow cleanly specifying the callback (`something.theValue_im { return someValue }`)
     */
    public void invoke(LongSupplier callback) {
        set(callback);
    }

    /**
     * Gets the current callback, or null if this IMValueLong has a fixed value
     */
    @Nullable
    public LongSupplier getCallback() {
        if(storage instanceof Storage.Callback) {
            return ((Storage.Callback) storage).callback;
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public Object getAnimatableValue() {
        return this.get();
    }

    @Override
    public void setAnimatableValue(@Nullable Object value) {
        this.setValue((long) value);
    }

    @Nullable
    @Override
    public Object getAnimatableCallback() {
        return this.getCallback();
    }

    @Override
    public void setAnimatableCallback(@NotNull Object supplier) {
        this.set((LongSupplier) supplier);
    }

    private static abstract class Storage {
        abstract long get();

        static class Fixed extends IMValueLong.Storage {
            long value;
            public Fixed(long value) {
                this.value = value;
            }

            @Override
            long get() {
                return value;
            }
        }

        static class Callback extends IMValueLong.Storage {
            LongSupplier callback;
            public Callback(LongSupplier callback) {
                this.callback = callback;
            }

            @Override
            long get() {
                return callback.getAsLong();
            }
        }
    }
}