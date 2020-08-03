<template>
  <div class="user"> 
        <div class="wrapC table">
            <div class="middle">
                <h1>회원가입 완료</h1>
                <h3>회원가입 인증 메일이 발송되었습니다. 이메일을 확인해주세요.</h3>
                <button class="btn" v-on:click="moveList">
                    <span>
                        메인화면
                    </span>
                </button>
            </div>
        </div>
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