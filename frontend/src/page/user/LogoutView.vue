<template>
  <div></div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
    name: 'Logout',
    methods: {
        ...mapActions(['logout']),
        kakaologout() {

        }
    },
    created() {
        this.logout()
    }
}
</script>
