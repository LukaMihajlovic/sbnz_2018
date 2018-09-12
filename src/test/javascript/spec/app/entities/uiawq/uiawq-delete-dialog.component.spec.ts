/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SbnzTestModule } from '../../../test.module';
import { UiawqDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/uiawq/uiawq-delete-dialog.component';
import { UiawqService } from '../../../../../../main/webapp/app/entities/uiawq/uiawq.service';

describe('Component Tests', () => {

    describe('Uiawq Management Delete Component', () => {
        let comp: UiawqDeleteDialogComponent;
        let fixture: ComponentFixture<UiawqDeleteDialogComponent>;
        let service: UiawqService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [UiawqDeleteDialogComponent],
                providers: [
                    UiawqService
                ]
            })
            .overrideTemplate(UiawqDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UiawqDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UiawqService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
