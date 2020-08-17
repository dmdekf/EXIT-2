<template>
  <div>
      <v-app id="inspire">
    <v-card
      class="mx-auto my-10"
      max-width="1000px"
    >
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
            회원가입 완료
          </div>
          <div class="text-h2"> 
            회원가입 인증 메일이 발송되었습니다. 이메일을 확인해주세요.
          </div>
          </v-col>
          <v-col cols="6">
            <v-progress-linear
              color="primary lighten-2"
              indeterminate
              rounded
              height="6"
            ></v-progress-linear>
          </v-col>
          <v-col cols="6">
          <v-btn color="lime" v-on:click="moveList">메인화면</v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-app>
  </div>
</template>

<script>
export default {
    methods: {
        moveList(){
                this.$router.push("/");
            }
    },
}
</script>

<style>

</style>