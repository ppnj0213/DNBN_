<template>
    <div id="loginSection">
        <div class="loginBox">
            <input type="text" class="inputId" name="userId" id="id" v-model="userId" autocomplete="off">
            <input type="password" class="inputPw" name="password" id="password" v-model="userPassword">
            <button class="loginBtn" @click="loginCheck">로그인</button>
            <a>아직 회원이 아니신가요 ? <span @click="moveSignUp">회원가입</span></a>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            userId: '',
            userPassword: '',
        }
    },

    methods: {
        async loginCheck() {
            console.log(this.userId)
            try {
                const res = await this.$axios.post(
                    '/api/members/login', {
                    userId: this.userId,
                    userPw: this.userPassword
                });
                console.log(res);
                // this.$router.push('/boards');
            } catch (e) {
                console.log(e)
            }
        },

        moveSignUp() {
            this.$router.push('/signup');
        }
    }
}
</script>

<style lang="scss" scoped>
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

    input {
        width: 600px;
        height: 80px;
        border-radius: 25px;
        background: #FFF;
        padding-left: 80px;
        margin-bottom: 32px;
    }

    .inputId {
        background-position: center left 20px;
        background-repeat: no-repeat;
        background-image: url('~/assets/img/login/id_icon.png');
        background-size: 32px;
    }

    .inputPw {
        background-position: center left 20px;
        background-repeat: no-repeat;
        background-image: url('~/assets/img/login/pw_icon.png');
        background-size: 32px;
    }

    .loginBtn {
        width: 360.747px;
        height: 76px;
        margin-bottom: 20px;
        border-radius: 25px;
        background: #FFF;
        font-size: 24px;
        font-style: normal;
        font-weight: 400;
    }

    a {
        font-size: 16px;
    }

    span {
        color: #FC0000;
        font-weight: bold;
        cursor: pointer;
    }
}
</style>
