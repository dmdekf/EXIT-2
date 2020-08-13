<template>
  <div></div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
    name: 'Logout',
    methods: {
        ...mapActions(['logout'])
    },
    created() {
        this.$store.replaceState(null)
        this.logout()
    }
}
</script>
