# Configure the Azure provider
terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.0.2"
    }
  }

  required_version = ">= 1.1.0"
}

provider "azurerm" {
  features {}
}
/*
supported Azure locations : australiacentral,australiacentral2,australiaeast,australiasoutheast,
brazilsouth,brazilsoutheast,brazilus,canadacentral,canadaeast,centralindia,
centralus,centraluseuap,eastasia,eastus,eastus2,eastus2euap,francecentral,francesouth,germanynorth,
germanywestcentral,israelcentral,italynorth,japaneast,japanwest,jioindiacentral,jioindiawest,koreacentral,
koreasouth,malaysiasouth,northcentralus,northeurope,norwayeast,norwaywest,polandcentral,qatarcentral,
southafricanorth,southafricawest,southcentralus,southeastasia,southindia,swedencentral,swedensouth,
switzerlandnorth,switzerlandwest,uaecentral,uaenorth,uksouth,ukwest,westcentralus,westeurope,westindia,
westus,westus2,westus3,austriaeast,chilecentral,eastusslv,israelnorthwest,malaysiawest,mexicocentral,
newzealandnorth,southeastasiafoundational,spaincentral,taiwannorth,taiwannorthwest

*/
resource "azurerm_resource_group" "rg" {
  name     = "myTFResourceGroup"
  location = "francecentral"

  tags = {
    Environment = "Terraform Getting Started"
    Team        = "DevOps"
  }
}

resource "azurerm_virtual_network" "vnet" {
  name                = "myTFVnet"
  address_space       = ["10.0.0.0/16"]
  location            = "francecentral"
  resource_group_name = azurerm_resource_group.rg.name
}


