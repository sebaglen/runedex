/*
 * Copyright (c) 2018, Sebastian Aglen Danielsen <https://github.com/sebaglen>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.runelite.client.plugins.runedex;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
@Singleton
public class APIManager
{

    private static final String API_BARE_URL = "https://us-central1-runedex-77a4c.cloudfunctions.net/addUserData";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final Gson GSON = new Gson();
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .pingInterval(30, TimeUnit.SECONDS)
            .build();

    private HashMap<String, Object> data = new HashMap<>();

    public void storeEvent(String eventType, Object event)
    {
        if (data.get(eventType) != null) {
            data.remove(eventType);
        }
        data.put(eventType, event);
    }

    protected void submitToAPI(RuneDexPluginConfiguration config)
    {
        if (config.shareLevels() == false) {
            data.remove("character");
        }
        if (config.shareBank() == false) {
            data.remove("bank");
        }

        if (data.isEmpty())
        {
            log.info("No data to Submit");
            return;
        }

        Request r = new Request.Builder()
                .url(API_BARE_URL)
                .post(RequestBody.create(JSON, GSON.toJson(data)))
                .build();

        data.clear();

        try (Response response = CLIENT.newCall(r).execute())
        {
            if (response.isSuccessful())
            {
                log.info("Successfully sent data");
            }
            else
            {
                log.info("Error sending data");
                log.info(response.body().toString());
            }
        }
        catch (IOException e)
        {
            log.info("IOException: {}", e.getMessage());
        }
    }
}
