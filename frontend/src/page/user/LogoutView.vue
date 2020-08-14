<template>
  <div></div>
</template>

<script>
import { mapGetters } from 'vuex'
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
