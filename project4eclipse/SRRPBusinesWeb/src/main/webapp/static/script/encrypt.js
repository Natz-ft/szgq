 /**
         * 加密（需要先加载lib/aes/aes.min.js文件）
         * @param word
         * @returns {*}
         */
        function encrypt(word){
            var key = CryptoJS.enc.Utf8.parse("123456qwerty1111");
            var srcs = CryptoJS.enc.Utf8.parse(word);
            var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
            return encrypted.toString();
        }

        /**
         * 解密
         * @param word
         * @returns {*}
         */
        function decrypt(word){
            var key = CryptoJS.enc.Utf8.parse("123456qwerty1111");
            var decrypt = CryptoJS.AES.decrypt(word, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
            return CryptoJS.enc.Utf8.stringify(decrypt).toString();
        }