<template>
    <div id="loginSection">
        <div class="loginBox">
            <input type="text" class="userId">
            <input type="text" class="userPass">
        </div>
    </div>
</template>

<script>
export default {
}
</script>

<style scoped>
#loginSection {
    display: flex;
    height: 100vh;
}

.loginBox {
    margin: 0 auto;
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-items: center;
}
</style>
