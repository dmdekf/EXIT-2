<template>
  <div class="user"> 
        <div class="wrapC table">
            <div class="middle">
                <h1>글쓰기 완료!!</h1>
                <h3>완료되었습니다~~</h3>
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