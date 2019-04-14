package com.zcoin.controllers;

import com.zcoin.domain.*;
import com.zcoin.services.RichAddressService;
import com.zcoin.services.RichAddressStatisticsService;
import com.zcoin.invoker.RichServiceInvoker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/zcoin")
@CrossOrigin(origins = "*")
@Api(value="zcoin", description="Zcoin Statistics")
public class RichAddressController {

    private RichServiceInvoker richServiceInvoker;

    private RichAddressService richAddressService;

    private RichAddressStatisticsService richAddressStatisticsService;

    @Autowired
    public void setProductService(RichServiceInvoker richServiceInvoker,
                                  RichAddressService richAddressService,
                                  RichAddressStatisticsService richAddressStatisticsService) {
        this.richServiceInvoker = richServiceInvoker;
        this.richAddressService = richAddressService;
        this.richAddressStatisticsService = richAddressStatisticsService;
    }

    @ApiOperation(value = "View a list of 1000 rich addresses",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/richAddress", method= RequestMethod.GET, produces = "application/json")
    public Iterable<RichAddressListModel> showRichList(Model model){
        return richAddressService.listAllRichAddress();
    }

    @ApiOperation(value = "Zcoin Rich address statistics",response = Iterable.class)
    @RequestMapping(value = "/getRichAddressStatistics", method= RequestMethod.GET, produces
            = "application/json")
    public Iterable<RichAddressStatisticsModel> showAddressDetails() throws IOException{
        return richAddressStatisticsService.listAllRichAddressStatistics();
    }

    @ApiOperation(value = "Zcoin total supply",response = BigDecimal.class)
    @RequestMapping(value = "/totalSupply", method= RequestMethod.GET, produces = "application/json")
    @Scheduled(cron = "0 0/10 * * * ?")
    public BigDecimal getTotalSupply() throws IOException{
        return richAddressService.getTotalSupply();
    }
}
