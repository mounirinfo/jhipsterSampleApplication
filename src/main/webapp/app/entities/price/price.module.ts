import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    PriceService,
    PricePopupService,
    PriceComponent,
    PriceDetailComponent,
    PriceDialogComponent,
    PricePopupComponent,
    PriceDeletePopupComponent,
    PriceDeleteDialogComponent,
    priceRoute,
    pricePopupRoute,
} from './';

const ENTITY_STATES = [
    ...priceRoute,
    ...pricePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PriceComponent,
        PriceDetailComponent,
        PriceDialogComponent,
        PriceDeleteDialogComponent,
        PricePopupComponent,
        PriceDeletePopupComponent,
    ],
    entryComponents: [
        PriceComponent,
        PriceDialogComponent,
        PricePopupComponent,
        PriceDeleteDialogComponent,
        PriceDeletePopupComponent,
    ],
    providers: [
        PriceService,
        PricePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationPriceModule {}
