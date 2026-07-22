package com.simple_shop.item_service.utils;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

// TODO Move to common util lib
public class AtomicBigInteger {
	private final AtomicReference<BigInteger> valueHolder;

	public AtomicBigInteger(final BigInteger initialValue) {
		this.valueHolder = new AtomicReference<>(initialValue);
	}

	public BigInteger addAndGet(final BigInteger delta) {
		return this.valueHolder.updateAndGet(current -> current.add(delta));
	}

	public BigInteger getAndIncrement() {
		return this.valueHolder.getAndUpdate(current -> current.add(BigInteger.ONE));
	}

	public BigInteger incrementAndGet() {
		return this.valueHolder.updateAndGet(current -> current.add(BigInteger.ONE));
	}

	public BigInteger get() {
		return this.valueHolder.get();
	}

	public boolean compareAndSet(final BigInteger expect, final BigInteger update) {
		return this.valueHolder.compareAndSet(expect, update);
	}
}
