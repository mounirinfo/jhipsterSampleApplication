import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Price } from './price.model';
import { PricePopupService } from './price-popup.service';
import { PriceService } from './price.service';

@Component({
    selector: 'jhi-price-dialog',
    templateUrl: './price-dialog.component.html'
})
export class PriceDialogComponent implements OnInit {

    price: Price;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private priceService: PriceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.price.id !== undefined) {
            this.subscribeToSaveResponse(
                this.priceService.update(this.price));
        } else {
            this.subscribeToSaveResponse(
                this.priceService.create(this.price));
        }
    }

    private subscribeToSaveResponse(result: Observable<Price>) {
        result.subscribe((res: Price) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Price) {
        this.eventManager.broadcast({ name: 'priceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-price-popup',
    template: ''
})
export class PricePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pricePopupService: PricePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pricePopupService
                    .open(PriceDialogComponent as Component, params['id']);
            } else {
                this.pricePopupService
                    .open(PriceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
