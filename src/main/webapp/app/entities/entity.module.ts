import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationCategoryModule } from './category/category.module';
import { JhipsterSampleApplicationProductModule } from './product/product.module';
import { JhipsterSampleApplicationPriceModule } from './price/price.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterSampleApplicationCategoryModule,
        JhipsterSampleApplicationProductModule,
        JhipsterSampleApplicationPriceModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
