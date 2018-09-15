package rs.ac.uns.ftn.sbnz.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(rs.ac.uns.ftn.sbnz.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Diagnosis.class.getName(), jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Doctor.class.getName(), jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Doctor.class.getName() + ".diagnoses", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Patient.class.getName(), jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Anamnesis.class.getName(), jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Anamnesis.class.getName() + ".diagnoses", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Anamnesis.class.getName() + ".allergiesIngredients", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Anamnesis.class.getName() + ".allergiesDrugs", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Ingredient.class.getName(), jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Ingredient.class.getName() + ".anamneses", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Drug.class.getName(), jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Drug.class.getName() + ".anamneses", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Drug.class.getName() + ".ingredients", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Ingredient.class.getName() + ".drugs", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Anamnesis.class.getName() + ".bacls", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Symptom.class.getName(), jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Symptom.class.getName() + ".diagnoses", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Diagnosis.class.getName() + ".drugs", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Diagnosis.class.getName() + ".symptoms", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Drug.class.getName() + ".diagnoses", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Uiawq.class.getName(), jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Disease.class.getName(), jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Disease.class.getName() + ".symptoms", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Symptom.class.getName() + ".diseases", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Diagnosis.class.getName() + ".diseases", jcacheConfiguration);
            cm.createCache(rs.ac.uns.ftn.sbnz.domain.Disease.class.getName() + ".diagnoses", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
