output "vpc_id" {
  value = aws_vpc.mb_vpc.id
}

output "public_subnet_ids" {
  value = aws_subnet.mb_public_subnet[0].id
}

output "private_subnet_ids" {
  value = aws_subnet.mb_private_subnet[0].id
}

output "eks_private_subnet_ids" {
  value = aws_subnet.mb_private_subnet[*].id
}