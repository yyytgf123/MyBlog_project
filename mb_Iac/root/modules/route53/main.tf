terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.54.1"
    }
  }
}

resource "aws_route53_zone" "mb_route53_zone" {
  force_destroy = true
  name = "cbnu.store"
}

resource "aws_route53_record" "mb_route53_a_record" {
  name    = "www.cbnu.store"
  type    = "A"
  zone_id = aws_route53_zone.mb_route53_zone.zone_id
  ttl = 300

  records = [var.private_ec2_ip]
}

# resource "aws_route53_zone" "mb_route53_ns_record" {
#   name = "cbnu.store"
#   type = "NS"
#   zone_id = aws_route53_zone.mb_route53_zone.id
#   ttl = 172800
#
#
# }