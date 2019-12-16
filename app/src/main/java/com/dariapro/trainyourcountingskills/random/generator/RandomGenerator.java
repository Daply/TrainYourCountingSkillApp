package com.dariapro.trainyourcountingskills.random.generator;

import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.random.arguments.GeneratorArguments;
import com.dariapro.trainyourcountingskills.random.provider.GeneratorProvider;

public class RandomGenerator {

    public Question generate(GeneratorArguments args, GeneratorProvider provider){
        return provider.generate(args);
    };

}
