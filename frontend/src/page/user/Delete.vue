<template>
  <div>
      <v-app id="inspire">
    <v-card
      class="mx-auto my-10"
      max-width="1000px"
    >
    <v-card-title class="white--text pl-12 pt-12">
            <div class="display-1 pl-12 pt-12">회원 탈퇴</div>
    </v-card-title>
      <v-container style="height: 600px;">
        <v-row
          class="fill-height"
          align-content="center"
          justify="center"
        >
          <v-col
            class="subtitle-1 text-center mb-5"
            cols="12"
          >
          <div class="text-h2"> 
            {{this.$store.state.login_user}}회원님의
            <p>데이터를 삭제 중입니다...</p> 
          </div>
          </v-col>
          <v-col cols="6">
            <v-progress-linear
              color="red lighten-2"
              indeterminate
              rounded
              height="6"
            ></v-progress-linear>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-app>
  </div>
</template>

<script>
import axios from 'axios';
import { mapActions } from 'vuex'
import SERVER from "@/api/api";
export default {
    destroyed () {
        this.uid = this.$store.state.login_user
        //(this.uid);
        axios({
            method:"delete",
            url:SERVER.URL+"/user/delete/"+this.uid,

        }).then((res)=>{
            let msg = "삭제 중 문제가 발생했습니다.";
            if(res.data.status){
                msg = "삭제가 완료되었습니다.";
                this.logout()
                this.$router.push("/");
                this.showAlert(1)
                }
            this.$router.push("/");
        })
        this.$router.push("/");
    },
    data: function(){
        return {
            uid:''
        };
    },
    methods: {
        ...mapActions(['logout'])
    },
}
</script>

<style>

</style>