terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.54.1"
    }
  }
}
/*----- vpc -----*/
resource "aws_vpc" "mb_vpc" {
  cidr_block = "10.0.0.0/16"

  enable_dns_hostnames = true // DNS 허용
  enable_dns_support = true //

  tags = {
    Name = "mb_vpc"
  }
}
/*---------------*/

/*----- subnet -----*/
resource "aws_subnet" "mb_public_subnet" {
  vpc_id = aws_vpc.mb_vpc.id
  count = length(var.availability_zone)
  cidr_block = cidrsubnet("10.0.0.0/16", 8, count.index)
  availability_zone = var.availability_zone[count.index]


  tags = {
    Name = "mb_public_subent${count.index + 1}"
    "kubernetes.io/cluster/mb_eks_cluster" = "shared"
    "kubernetes.io/role/elb"               = "1"
  }
}

resource "aws_subnet" "mb_private_subnet" {
  vpc_id = aws_vpc.mb_vpc.id
  count = length(var.availability_zone) * 2
  cidr_block = cidrsubnet("10.0.0.0/16", 8, count.index + 2)
  availability_zone = var.availability_zone[floor(count.index / 2)]

  tags = {
    Name = "mb_private_subnet${count.index + 1}"
    "kubernetes.io/cluster/mb_eks_cluster" = "shared"
    "kubernetes.io/role/internal-elb"      = "1"
  }
}
/*----------------------*/

/*----- routing table -----*/
resource "aws_route_table" "mb_public_routing_table" {
  vpc_id = aws_vpc.mb_vpc.id

  tags = {
    Name = "mb_public_routing_table"
  }
}

resource "aws_route_table" "mb_private_routing_table" {
  vpc_id = aws_vpc.mb_vpc.id

  tags = {
    Name = "mb_private_routing_table"
  }
}
/*------------------------------*/

/*----- routing table + gateway -----*/
resource "aws_route" "mb_public_route" {
  route_table_id = aws_route_table.mb_public_routing_table.id
  destination_cidr_block = "0.0.0.0/0"
  gateway_id = aws_internet_gateway.mb_internet_gateway.id
}

resource "aws_route" "mb_private_route" {
  route_table_id = aws_route_table.mb_private_routing_table.id
  destination_cidr_block = "0.0.0.0/0"
  nat_gateway_id = aws_nat_gateway.mb_nat_gateway.id
}
/*-------------------------------------*/

/*----- routing table + subnet -----*/
resource "aws_route_table_association" "mb_public_routing_connection" {
  count = length(aws_subnet.mb_public_subnet)
  route_table_id =   aws_route_table.mb_public_routing_table.id
  subnet_id = aws_subnet.mb_public_subnet[count.index].id
}

resource "aws_route_table_association" "mb_private_routing_connection" {
  count = length(aws_subnet.mb_private_subnet)
  route_table_id = aws_route_table.mb_private_routing_table.id
  subnet_id = aws_subnet.mb_private_subnet[count.index].id
}
/*-----------------------------------*/

/*----- internet gateway -----*/
resource "aws_internet_gateway" "mb_internet_gateway" {
  vpc_id = aws_vpc.mb_vpc.id

  tags = {
    Name = "mb_internet_gateway"
  }
}
/*----------------------------*/

/*----- Nat gateway -----*/
resource "aws_nat_gateway" "mb_nat_gateway" {
  allocation_id = aws_eip.mb_nat_gateway_eip.id
  subnet_id = aws_subnet.mb_public_subnet[0].id

  tags = {
    Name = "mb_nat_gateway"
  }
}

resource "aws_eip" "mb_nat_gateway_eip" {
  vpc = true

  tags = {
    Name = "mb_nat_gateway_eip"
  }
}
/*----------------------------*/
