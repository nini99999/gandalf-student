<template>
    <div>
        <div class="input" v-if="!readonly">
            {{file?file.name:''}}
            <input type="file" :accept="accept" @change="onFileChange">
        </div>
        <div class="preview" v-if="file || value.url">
            <img :src="url || value.url" v-if="isImage">
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            value: {},
            accept: {
                type: String,
                default: 'image/*'
            },
            readonly: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                file: null,
                url: ''
            }
        },
        computed: {
            isImage() {
                return /^image/.test(this.accept)
            }
        },
        methods: {
            onFileChange(e) {
                this.file = e.target.files[0];
                if (this.file) {
                    this.url = URL.createObjectURL(this.file);
                } else {
                    this.url = '';
                }
                this.$emit('input', {url: this.value.url, file: this.file});
            }
        }
    }
</script>

<style scoped lang="scss">
    .input {
        position: relative;
        line-height: 32px;

        input {
            position: absolute;
            width: 100%;
            height: 100%;
            opacity: 0;
        }
    }

    .preview {
        margin-top: 10px;

        img {
            max-width: 300px;
            max-height: 300px;
        }
    }
</style>