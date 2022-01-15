/**
 * Copyright 2022 Pinterest, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pinterest.memq.client.commons2.retry;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class FullJitterRetryStrategy extends RetryStrategy {

  private long baseRetryIntervalMs = 200;
  private long maxRetryIntervalMs = 10000;
  private int maxAttempts = Integer.MAX_VALUE - 1;

  public FullJitterRetryStrategy() {
  }

  public void setBaseRetryIntervalMs(long baseRetryIntervalMs) {
    this.baseRetryIntervalMs = baseRetryIntervalMs;
  }

  public void setMaxRetryIntervalMs(long maxRetryIntervalMs) {
    this.maxRetryIntervalMs = maxRetryIntervalMs;
  }

  public void setMaxAttempts(int maxAttempts) {
    this.maxAttempts = maxAttempts;
  }

  @Override
  public Duration calculateNextRetryInterval(int attempts) {
    if (attempts >= maxAttempts ) {
      return null;
    }
    long upper = Math.min((Math.round(Math.pow(2, attempts) * baseRetryIntervalMs)), maxRetryIntervalMs);
    return Duration.ofMillis(ThreadLocalRandom.current().nextLong(1, upper + 1));
  }
}
