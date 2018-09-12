/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbnzTestModule } from '../../../test.module';
import { UiawqComponent } from '../../../../../../main/webapp/app/entities/uiawq/uiawq.component';
import { UiawqService } from '../../../../../../main/webapp/app/entities/uiawq/uiawq.service';
import { Uiawq } from '../../../../../../main/webapp/app/entities/uiawq/uiawq.model';

describe('Component Tests', () => {

    describe('Uiawq Management Component', () => {
        let comp: UiawqComponent;
        let fixture: ComponentFixture<UiawqComponent>;
        let service: UiawqService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [UiawqComponent],
                providers: [
                    UiawqService
                ]
            })
            .overrideTemplate(UiawqComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UiawqComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UiawqService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Uiawq(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.uiawqs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
