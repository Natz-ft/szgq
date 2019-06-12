package com.icfcc.credit.platform.qcloudsms;

import com.github.qcloudsms.SmsResultBase;
import com.github.qcloudsms.httpclient.HTTPResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;


public class SmsStatusPullReplyResult extends SmsResultBase {

    public class Reply {

        public String nationcode;
        public String mobile;
        public String text;
        public String sign;
        public long time;

        public String getNationcode() {
			return nationcode;
		}

		public void setNationcode(String nationcode) {
			this.nationcode = nationcode;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		@Override
        public String toString(){
            String[] fields = {"nationcode", "mobile", "text", "sign", "time"};
            return (new JSONObject(this, fields)).toString();
        }

        public Reply parse(JSONObject json) throws JSONException {

            nationcode = json.getString("nationcode");
            mobile = json.getString("mobile");
            text = json.getString("text");
            sign = json.getString("sign");
            time = json.getLong("time");

            return this;
        }
    }

    public int result;
    public String errMsg;
    public int count;
    public ArrayList<Reply> replys;

    public SmsStatusPullReplyResult() {
        this.errMsg = "";
        this.count = 0;
        this.replys = new ArrayList<Reply>();
    }

    @Override
    public SmsStatusPullReplyResult parseFromHTTPResponse(HTTPResponse response)
            throws JSONException {

        JSONObject json = parseToJson(response);

        result = json.getInt("result");
        errMsg = json.getString("errmsg");
        if (json.has("count")) {
            count = json.getInt("count");
        }

        if (json.has("data") && !json.isNull("data")) {
            JSONArray data = json.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                replys.add((new Reply()).parse(data.getJSONObject(i)));
            }
        }

        return this;
    }

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<Reply> getReplys() {
		return replys;
	}

	public void setReplys(ArrayList<Reply> replys) {
		this.replys = replys;
	}
    
}

