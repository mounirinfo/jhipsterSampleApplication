import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PriceComponent } from './price.component';
import { PriceDetailComponent } from './price-detail.component';
import { PricePopupComponent } from './price-dialog.component';
import { PriceDeletePopupComponent } from './price-delete-dialog.component';

export const priceRoute: Routes = [
    {
        path: 'price',
        component: PriceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prices'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'price/:id',
        component: PriceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pricePopupRoute: Routes = [
    {
        path: 'price-new',
        component: PricePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'price/:id/edit',
        component: PricePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'price/:id/delete',
        component: PriceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
