module.exports = function override(config, env) {
    // find and change ts-loader
    config.module.rules = config.module.rules.map((rule) => {
        if (rule.oneOf) {
            rule.oneOf = rule.oneOf.map((oneOf) => {
                if (oneOf.test && oneOf.test.toString().includes('tsx')) {
                    oneOf.include = undefined;
                    oneOf.exclude = /node_modules/;
                }
                return oneOf;
            });
        }
        return rule;
    });

    console.log(JSON.stringify(config, null, 2))
    return config;
};
