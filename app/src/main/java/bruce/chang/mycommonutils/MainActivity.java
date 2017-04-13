package bruce.chang.mycommonutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bruce.chang.mylibrary.CCGLogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CCGLogUtils.init(true, "CCCCCCCC");
        CCGLogUtils.e("\n" +
                "{\"result\":1,\"message\":\"请求成功\",\"data\":[{\"id\":\"24899\",\"createDate\":\"2017-04-02 13:35:29\",\"module\":13,\"customerid\":2679,\"feihaid\":\"59285\",\"itemid\":-1,\"itemdesc\":\"2\",\"nickname\":\"王香宇\",\"headico\":\"http://admin.fithub.cc/FeiHa/userfiles/jinj/thumbs/images/users/2016/11/444781220892046283.jpg\",\"content\":\"效果棒棒哒，一天不练睡不踏实，虽然瘦，但是肉不结实，所以再接再厉!\",\"location\":\"随州市\",\"skims\":281,\"lauds\":0,\"comments\":0,\"sort\":0,\"recordId\":0,\"type\":1,\"islaud\":\"0\",\"pics\":[\"http://ws-ugc.fithub.cc/app/59285/dynamic/24899/b13285bcf20646f99fba977f83a956d0.jpg\",\"http://ws-ugc.fithub.cc/app/59285/dynamic/24899/c3fc11bc14cb402e85d2820a473b1ba0.jpg\",\"http://ws-ugc.fithub.cc/app/59285/dynamic/24899/5476f3984251441cbc0d714f831796b4.jpg\",\"http://ws-ugc.fithub.cc/app/59285/dynamic/24899/402fd653da774e91be612e7e14245de2.jpg\"],\"videos\":[],\"fullHeadico\":\"http://admin.fithub.cc/FeiHa/userfiles/jinj/thumbs/images/users/2016/11/444781220892046283.jpg\",\"createDateReadable\":\"11天前\"}],\"success\":true,\"timestamp\":1492072089427}");
    }
}
